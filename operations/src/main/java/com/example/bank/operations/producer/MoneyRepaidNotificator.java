package com.example.bank.operations.producer;

import com.example.bank.operations.pojo.MoneyRepaid;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class MoneyRepaidNotificator {

    private final BinderAwareChannelResolver binderAwareChannelResolver;

    public MoneyRepaidNotificator(BinderAwareChannelResolver binderAwareChannelResolver) {
        this.binderAwareChannelResolver = binderAwareChannelResolver;
    }

    public void send(MoneyRepaid moneyRepaid) {
        MessageChannel messageChannel = binderAwareChannelResolver.resolveDestination("operation-statement-out");
        messageChannel.send(MessageBuilder.withPayload(moneyRepaid)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .setHeader("type", "create").build());
    }

}
