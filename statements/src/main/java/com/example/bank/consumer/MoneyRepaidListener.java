package com.example.bank.consumer;

import com.example.bank.model.Statement;
import com.example.bank.payload.request.MoneyRepaid;
import com.example.bank.repository.StatementRepository;
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
    private StatementRepository statementService;

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='create'")
    public void handle(@Payload MoneyRepaid moneyRepaid) {
        //LOGGER.info("Mensaje recibido: " + message);
        Statement statement = statementService.findFirstByCardNrOrderByIdDesc(moneyRepaid.getCardNo());
        statement.setClosed(true);
    }

}
