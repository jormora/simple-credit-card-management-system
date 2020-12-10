package com.example.bank.producer;

import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class StatementProducer {

    private final BinderAwareChannelResolver binderAwareChannelResolver;

    public StatementProducer(BinderAwareChannelResolver binderAwareChannelResolver) {
        this.binderAwareChannelResolver = binderAwareChannelResolver;
    }

    public void send(String message) {
        MessageChannel messageChannel = binderAwareChannelResolver.resolveDestination("operation-statement-out");
        messageChannel.send(MessageBuilder.withPayload(message)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .setHeader("type", "create").build());
    }

}
