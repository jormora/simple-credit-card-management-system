package com.example.bank.cards.consumer;

import com.example.bank.cards.model.PlasticCard;
import com.example.bank.cards.pojo.PlasticCardPOJO;
import com.example.bank.cards.service.PlasticCardService;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(Sink.class)
public class PlasticCardConsumer {

    private final PlasticCardService plasticCardService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlasticCardConsumer.class);

    public PlasticCardConsumer(PlasticCardService plasticCardService) {
        this.plasticCardService = plasticCardService;
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='create'")
    public void handle(@Payload PlasticCardPOJO plasticCardPOJO) {
        if (!PlasticCardPOJO.isRequestEntityRight(plasticCardPOJO) ||
            this.plasticCardService.findByCardNo(plasticCardPOJO.getCardNo()) != null) {
            LOGGER.info("La información para crear la tarjeta plástica es inválida");
            return;
        }
        PlasticCard plasticCard = PlasticCardPOJO.mapRequestEntity(plasticCardPOJO);
        this.plasticCardService.save(plasticCard);
        LOGGER.info("Tarjeta plastica creada");
    }

}
