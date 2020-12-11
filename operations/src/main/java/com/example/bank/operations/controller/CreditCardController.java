package com.example.bank.operations.controller;

import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.model.User;
import com.example.bank.operations.pojo.CreditCardPOJO;
import com.example.bank.operations.producer.MoneyRepaidNotificator;
import com.example.bank.operations.service.CreditCardService;
import com.example.bank.operations.service.UserService;
import com.example.bank.operations.service.WithdrawalService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreditCardController {

    private static final String CREDIT_CARD_NOT_FOUND = "Tarjeta de crédito no encontrada";

    private static final String AMOUNT_IS_GREATER_THAN_USED_LIMIT = "La cantidad para pagar es mayor que la deuda";

    private static final String SUCCESSFUL_PAYMENT = "Pago exitoso";

    private static final String INVALID_DATA_FORMAT = "El formato de los datos no es válido";

    private static final String CREDIT_CARD_CREATED = "Tarjeta de crédito creada";

    private static final String CREDIT_CARD_DELETED = "Tarjeta de crédito eliminada";

    private static final String USER_ALREADY_HAS_CREDIT_CARD = "El usuario ya tiene una tarjeta de crédito";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);

    private final UserService userService;

    private final CreditCardService creditCardService;

    private final WithdrawalService withdrawalService;

    private final MoneyRepaidNotificator moneyRepaidNotificator;

    public CreditCardController(UserService userService, CreditCardService creditCardService, WithdrawalService withdrawalService, MoneyRepaidNotificator moneyRepaidNotificator) {
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.withdrawalService = withdrawalService;
        this.moneyRepaidNotificator = moneyRepaidNotificator;
    }

    @GetMapping(path = "/credit-card/{id}")
    public ResponseEntity<CreditCard> getCreditCard(@PathVariable Long id) {
        CreditCard creditCard = this.creditCardService.findById(id);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(creditCard);
    }



    @PatchMapping(path = "/credit-card/{id}/charge-back/{amount}")
    public ResponseEntity<String> chargeBack(@PathVariable Long id, @PathVariable Long amount) {
        CreditCard creditCard = this.creditCardService.findById(id);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(CREDIT_CARD_NOT_FOUND);
        }
        if (!creditCard.checkAmountIsLessThanUsedLimit(amount)) {
            LOGGER.info(AMOUNT_IS_GREATER_THAN_USED_LIMIT);
            return ResponseEntity.badRequest().body(AMOUNT_IS_GREATER_THAN_USED_LIMIT);
        }
        creditCard.chargeBack(amount);
        this.creditCardService.save(creditCard);
        return ResponseEntity.ok(SUCCESSFUL_PAYMENT);
    }

    @PostMapping(path = "/credit-card/create/{username}")
    public ResponseEntity<String> createCreditCard(@PathVariable String username, @RequestBody CreditCardPOJO creditCardPOJO) {
        User user = this.userService.findByUsername(username);
        if (user == null) {
            LOGGER.info(UserController.USER_NOT_FOUND);
            return ResponseEntity.badRequest().body(UserController.USER_NOT_FOUND);
        }
        if (user.getCreditCard() != null) {
            LOGGER.info(USER_ALREADY_HAS_CREDIT_CARD);
            return ResponseEntity.badRequest().body(USER_ALREADY_HAS_CREDIT_CARD);
        }
        if (!CreditCardPOJO.isRequestEntityRight(creditCardPOJO)) {
            LOGGER.info(INVALID_DATA_FORMAT);
            return ResponseEntity.badRequest().body(INVALID_DATA_FORMAT);
        }
        CreditCard creditCard = CreditCardPOJO.mapRequestEntity(creditCardPOJO, user);
        user.setCreditCard(creditCard);
        this.userService.save(user);
        return ResponseEntity.ok(CREDIT_CARD_CREATED);
    }

    @DeleteMapping(path = "/credit-card/{id}/delete/{username}")
    public ResponseEntity<String> deleteCreditCard(@PathVariable Long id, @PathVariable String username) {
        CreditCard creditCard = this.creditCardService.findById(id);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(CREDIT_CARD_NOT_FOUND);
        }
        User user = this.userService.findByUsername(username);
        if (user == null) {
            LOGGER.info(UserController.USER_NOT_FOUND);
            return ResponseEntity.badRequest().body(UserController.USER_NOT_FOUND);
        }
        user.setCreditCard(null);
        this.userService.save(user);
        this.withdrawalService.deleteAllByCreditCard(creditCard);
        this.creditCardService.deleteById(id);
        return ResponseEntity.ok(CREDIT_CARD_DELETED);
    }

}
