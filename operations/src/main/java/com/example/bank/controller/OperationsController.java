package com.example.bank.controller;

import com.example.bank.pojo.PlasticCardPOJO;
import com.example.bank.producer.OperationsProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationsController {

    private final OperationsProducer operationsProducer;

    public OperationsController(OperationsProducer operationsProducer) {
        this.operationsProducer = operationsProducer;
    }

    @PostMapping(path = "/plastic-card/create")
    public ResponseEntity<String> createPlasticCard(@RequestBody PlasticCardPOJO plasticCardPOJO) {
        this.operationsProducer.getSource().output().send(MessageBuilder.withPayload(plasticCardPOJO)
            .setHeader("type", "create")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());
        return ResponseEntity.ok("Enviado");
    }

}
