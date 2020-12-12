package com.example.bank.users.producer;

import com.example.bank.users.pojo.CreditCardPOJO;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class OperationsNotifier {

    private final BinderAwareChannelResolver binderAwareChannelResolver;

    public OperationsNotifier(BinderAwareChannelResolver binderAwareChannelResolver) {
        this.binderAwareChannelResolver = binderAwareChannelResolver;
    }

    public void send(CreditCardPOJO creditCardPOJO) {
        MessageChannel messageChannel = this.binderAwareChannelResolver.resolveDestination("credit-card-operations");
        messageChannel.send(MessageBuilder.withPayload(creditCardPOJO)
            .setHeader("type", "create")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());
    }

}
