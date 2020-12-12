package com.example.bank.users.producer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(Source.class)
public class PlasticCardNotifier {

    private final Source source;

    public PlasticCardNotifier(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

}
