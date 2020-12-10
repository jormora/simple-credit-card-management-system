package com.example.bank.consumer;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(Sink.class)
public class StatementConsumer {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StatementConsumer.class);

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='create'")
    public void handle(@Payload String message) {
        LOGGER.info("Mensaje recibido: " + message);
    }

}
