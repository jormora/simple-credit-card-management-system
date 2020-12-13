package com.example.bank.operations.controller;

import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.service.CreditCardService;
import com.example.bank.operations.service.WithdrawalService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CreditCardController {

    private static final String CREDIT_CARD_NOT_FOUND = "Tarjeta de crédito no encontrada";

    private static final String CREDIT_CARD_DELETED = "Tarjeta de crédito eliminada";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);

    private final CreditCardService creditCardService;

    private final WithdrawalService withdrawalService;

    public CreditCardController(CreditCardService creditCardService, WithdrawalService withdrawalService) {
        this.creditCardService = creditCardService;
        this.withdrawalService = withdrawalService;
    }

    @GetMapping(path = "/credit-card/{cardNo}")
    public ResponseEntity<CreditCard> getCreditCard(@PathVariable String cardNo) {
        CreditCard creditCard = this.creditCardService.findByCardNo(cardNo);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(creditCard);
    }

    @GetMapping(path = "/credit-card/numbers")
    public ResponseEntity<List<String>> getAllCreditCardsNumbers() {
        List<CreditCard> creditCards = this.creditCardService.findAll();
        List<String> numbers = new ArrayList<>();
        for (CreditCard creditCard : creditCards) {
            numbers.add(creditCard.getCardNo());
        }
        return ResponseEntity.ok(numbers);
    }

    @DeleteMapping(path = "/credit-card/{cardNo}/delete")
    public ResponseEntity<String> deleteCreditCard(@PathVariable String cardNo) {
        CreditCard creditCard = this.creditCardService.findByCardNo(cardNo);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(CREDIT_CARD_NOT_FOUND);
        }
        this.withdrawalService.deleteAllByCreditCard(creditCard);
        this.creditCardService.deleteByCardNo(cardNo);
        return ResponseEntity.ok(CREDIT_CARD_DELETED);
    }

}
