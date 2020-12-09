package com.example.bank.producer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(Source.class)
public class OperationsProducer {

    private final Source source;

    public OperationsProducer(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

}
