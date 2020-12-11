package com.example.bank.controller;

import com.example.bank.model.CreditCard;
import com.example.bank.model.Withdrawal;
import com.example.bank.pojo.WithdrawRequest;
import com.example.bank.service.CreditCardService;
import com.example.bank.service.WithdrawalService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController("/withdrawals")
public class WithdrawalController {

    private static final String SUCCESSFUL_WITHDRAW = "Retiro exitoso";

    private static final String NOT_ENOUGH_BALANCE = "La tarjeta no tiene balance suficiente para el retiro";

    private static final String CREDIT_CARD_NOT_FOUND = "Tarjeta de crédito no encontrada";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WithdrawalController.class);

    private final CreditCardService creditCardService;

    private final WithdrawalService withdrawalService;

    public WithdrawalController(CreditCardService creditCardService, WithdrawalService withdrawalService) {
        this.creditCardService = creditCardService;
        this.withdrawalService = withdrawalService;
    }

    @GetMapping(path = "/{cardNo}")
    public ResponseEntity<List<Withdrawal>> getCreditCardWithdrawals(@PathVariable String cardNo) {
        CreditCard creditCard = this.creditCardService.findByCardNo(cardNo);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(null);
        }
        List<Withdrawal> withdrawals = this.withdrawalService.findAllByCreditCard(creditCard);
        return ResponseEntity.ok(withdrawals);
    }

    @PostMapping(path = "/{cardNo}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String cardNo, @RequestBody WithdrawRequest withdrawRequest) {
        CreditCard creditCard = this.creditCardService.findByCardNo(cardNo);
        if (creditCard == null) {
            LOGGER.info(CREDIT_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(CREDIT_CARD_NOT_FOUND);
        }
        if (!creditCard.isThereEnoughMoneyToWithdraw(withdrawRequest.getAmount())) {
            LOGGER.info(NOT_ENOUGH_BALANCE);
            return ResponseEntity.badRequest().body(NOT_ENOUGH_BALANCE);
        }
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setCreditCardW(creditCard);
        withdrawal.setAmount(withdrawRequest.getAmount());
        withdrawal.setDateTime(OffsetDateTime.now());
        this.withdrawalService.save(withdrawal);
        creditCard.withdraw(withdrawRequest.getAmount());
        this.creditCardService.save(creditCard);
        return ResponseEntity.ok(SUCCESSFUL_WITHDRAW);
    }
}
