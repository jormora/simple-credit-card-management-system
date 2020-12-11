package com.example.bank.generator;

import com.example.bank.model.CreditCard;
import com.example.bank.model.Statement;
import com.example.bank.payload.request.Withdrawal;
import com.example.bank.payload.request.Withdrawals;
import com.example.bank.service.CreditCardService;
import com.example.bank.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatementGenerator {
    private final StatementService statementService;
    @Autowired
    private CreditCardService creditCardService;

    public StatementGenerator(StatementService statementService) {
        this.statementService = statementService;
    }

    @Scheduled
    public void generateStatements() {
        allCardNumbers()
                .forEach(this::generateIfNeeded);
    }

    private void generateIfNeeded(String cardNo) {
        //query to card-operations
        //if 200 OK - generate and statement
        String uri = "http://localhost:8080/credit-cards/withdrawals/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Withdrawals> response = restTemplate.getForEntity(uri+cardNo,Withdrawals.class);
        if(response.getStatusCode()== HttpStatus.ACCEPTED){
            Withdrawals withdrawals = response.getBody();
            Statement statement =  new Statement();
            statement.setAmount(BigDecimal.ZERO);
            statement.setCardNr(cardNo);
            statement.setClosed(false);
            assert withdrawals != null;
            for(Withdrawal withdrawal:withdrawals.getWithdrawals()){
                statement.setAmount(statement.getAmount().add(BigDecimal.valueOf(withdrawal.getAmount())));
            }
            statementService.save(statement);
        }
    }

    private List<String> allCardNumbers() {
        List<CreditCard> creditCards = creditCardService.findAll();
        ArrayList<String> cardNumbers = new ArrayList<>();
        creditCards.forEach(creditCard->
            cardNumbers.add(String.valueOf(creditCard.getId()))
        );
        return cardNumbers;
    }

}

