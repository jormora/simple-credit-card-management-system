package com.example.bank.statements.consumer;

import com.example.bank.statements.model.Statement;
import com.example.bank.statements.payload.request.MoneyRepaid;
import com.example.bank.statements.repository.StatementRepository;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class MoneyRepaidListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MoneyRepaidListener.class);
    private final StatementRepository statementService;

    public MoneyRepaidListener(StatementRepository statementService) {
        this.statementService = statementService;
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='close'")
    public void close(@Payload MoneyRepaid moneyRepaid) {
        Statement statement = this.statementService.findFirstByCardNrOrderByIdDesc(moneyRepaid.getCardNo());
        if (statement != null) {
            statement.setClosed(true);
            this.statementService.save(statement);
            LOGGER.info("Statement closed");
        }
    }

}
