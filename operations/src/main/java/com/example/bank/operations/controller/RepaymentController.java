package com.example.bank.operations.controller;

import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.pojo.MoneyRepaid;
import com.example.bank.operations.pojo.RepaymentRequest;
import com.example.bank.operations.producer.MoneyRepaidNotifier;
import com.example.bank.operations.service.CreditCardService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/repayment")
public class RepaymentController {

    private static final String CREDIT_CARD_NOT_FOUND = "Tarjeta de crédito no encontrada";

    private static final String REPAYMENT_DONE = "El pago se ha realizado correctamente";

    private final MoneyRepaidNotifier moneyRepaidNotifier;

    private final CreditCardService creditCardService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RepaymentController.class);

    RepaymentController(MoneyRepaidNotifier moneyRepaidNotifier, CreditCardService creditCardService) {
        this.moneyRepaidNotifier = moneyRepaidNotifier;
        this.creditCardService = creditCardService;
    }

    @PostMapping("/repay/{cardNo}")
    ResponseEntity<String> repay(@PathVariable String cardNo, @RequestBody RepaymentRequest repaymentRequest) {
        CreditCard creditCard = this.creditCardService.findByCardNo(cardNo);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(CREDIT_CARD_NOT_FOUND);
        }
        creditCard.repay(repaymentRequest.getAmount());
        this.creditCardService.save(creditCard);
        moneyRepaidNotifier.close(new MoneyRepaid(cardNo, repaymentRequest.getAmount()));
        return ResponseEntity.badRequest().body(REPAYMENT_DONE);
    }
}
