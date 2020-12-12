package com.example.bank.statements.generator;

import com.example.bank.statements.model.Statement;
import com.example.bank.statements.payload.request.CreditCards;
import com.example.bank.statements.payload.request.Withdrawal;
import com.example.bank.statements.payload.request.Withdrawals;
import com.example.bank.statements.service.StatementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class StatementGenerator {
    private final StatementService statementService;

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
        String uri = "http://localhost:8090/withdrawals/";
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
                statement.setAmount(statement.getAmount().add(withdrawal.getAmount()));
            }
            statementService.save(statement);
        }
    }

    private List<String> allCardNumbers() {
        String uri = "http://localhost:8090/credit-card/numbers";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreditCards> response = restTemplate.getForEntity(uri, CreditCards.class);
        return Objects.requireNonNull(response.getBody()).getNumbers();
    }

}

