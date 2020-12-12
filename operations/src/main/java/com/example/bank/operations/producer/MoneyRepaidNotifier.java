package com.example.bank.operations.producer;

import com.example.bank.operations.pojo.MoneyRepaid;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@EnableBinding(Source.class)
@Service
public class MoneyRepaidNotifier {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MoneyRepaidNotifier.class);

    private final Source source;

    public MoneyRepaidNotifier(Source source) {
        this.source = source;
    }

    public void close(MoneyRepaid moneyRepaid) {
        MessageChannel messageChannel = source.output();
        messageChannel.send(MessageBuilder.withPayload(moneyRepaid)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .setHeader("type", "close").build());
    }

}
