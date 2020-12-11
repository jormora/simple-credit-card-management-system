package com.example.bank.operations.producer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(Source.class)
public class PlasticCardProducer {

    private final Source source;

    public PlasticCardProducer(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

}
