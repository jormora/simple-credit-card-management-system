package com.example.bank.controller;

import com.example.bank.model.CreditCard;
import com.example.bank.model.User;
import com.example.bank.model.Withdrawal;
import com.example.bank.pojo.CreditCardPOJO;
import com.example.bank.service.CreditCardService;
import com.example.bank.service.UserService;
import com.example.bank.service.WithdrawalService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class CreditCardController {

    private static final String CREDIT_CARD_NOT_FOUND = "Tarjeta de crédito no encontrada";

    private static final String NOT_ENOUGH_BALANCE = "La tarjeta no tiene balance suficiente para el retiro";

    private static final String AMOUNT_IS_GREATER_THAN_USED_LIMIT = "La cantidad para pagar es mayor que la deuda";

    private static final String SUCCESSFUL_WITHDRAW = "Retiro exitoso";

    private static final String SUCCESSFUL_PAYMENT = "Pago exitoso";

    private static final String INVALID_DATA_FORMAT = "El formato de los datos no es válido";

    private static final String CREDIT_CARD_CREATED = "Tarjeta de crédito creada";

    private static final String CREDIT_CARD_DELETED = "Tarjeta de crédito eliminada";

    private static final String USER_ALREADY_HAS_CREDIT_CARD = "El usuario ya tiene una tarjeta de crédito";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);

    private final UserService userService;

    private final CreditCardService creditCardService;

    private final WithdrawalService withdrawalService;

    public CreditCardController(UserService userService, CreditCardService creditCardService, WithdrawalService withdrawalService) {
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.withdrawalService = withdrawalService;
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

    @GetMapping(path = "/credit-card/{id}/withdrawals")
    public ResponseEntity<List<Withdrawal>> getCreditCardWithdrawals(@PathVariable Long id) {
        CreditCard creditCard = this.creditCardService.findById(id);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(null);
        }
        List<Withdrawal> withdrawals = this.withdrawalService.findAllByCreditCard(creditCard);
        return ResponseEntity.ok(withdrawals);
    }

    @PatchMapping(path = "/credit-card/{id}/withdraw/{code}/{amount}")
    public ResponseEntity<String> withdraw(@PathVariable Long id, @PathVariable Long code, @PathVariable Long amount) {
        CreditCard creditCard = this.creditCardService.findById(id);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(CREDIT_CARD_NOT_FOUND);
        }
        if (!creditCard.isThereEnoughMoneyToWithdraw(amount)) {
            LOGGER.info(NOT_ENOUGH_BALANCE);
            return ResponseEntity.badRequest().body(NOT_ENOUGH_BALANCE);
        }
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(code);
        withdrawal.setCreditCardW(creditCard);
        withdrawal.setAmount(amount);
        withdrawal.setDateTime(OffsetDateTime.now());
        this.withdrawalService.save(withdrawal);
        creditCard.withdraw(amount);
        this.creditCardService.save(creditCard);
        return ResponseEntity.ok(SUCCESSFUL_WITHDRAW);
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
