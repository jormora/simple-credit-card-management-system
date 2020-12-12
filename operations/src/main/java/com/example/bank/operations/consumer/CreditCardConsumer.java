package com.example.bank.operations.consumer;

import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.pojo.CreditCardPOJO;
import com.example.bank.operations.service.CreditCardService;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(Sink.class)
public class CreditCardConsumer {

    private final CreditCardService creditCardService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CreditCardConsumer.class);

    public CreditCardConsumer(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='create'")
    public void handle(@Payload CreditCardPOJO creditCardPOJO) {
        if (!CreditCardPOJO.isRequestEntityRight(creditCardPOJO) ||
            this.creditCardService.findByCardNo(creditCardPOJO.getCardNo()) != null) {
            LOGGER.info("La información para crear la tarjeta de crédito es inválida");
            return;
        }
        CreditCard creditCard = CreditCardPOJO.mapRequestEntity(creditCardPOJO);
        this.creditCardService.save(creditCard);
        LOGGER.info("Tarjeta de crédito creada");
    }
}
