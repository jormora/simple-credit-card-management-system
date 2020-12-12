package com.example.bank.cards.controller;

import com.example.bank.cards.model.PlasticCard;
import com.example.bank.cards.service.PlasticCardService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlasticCardController {

    private static final String PLASTIC_CARD_NOT_FOUND = "Tarjeta plástica no encontrada";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlasticCardController.class);

    private final PlasticCardService plasticCardService;

    public PlasticCardController(PlasticCardService plasticCardService) {
        this.plasticCardService = plasticCardService;
    }

    @GetMapping(path = "/plastic-card/{cardNo}")
    public ResponseEntity<PlasticCard> getPlasticCard(@PathVariable String cardNo) {
        PlasticCard plasticCard = this.plasticCardService.findByCardNo(cardNo);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(plasticCard);
    }

    @PatchMapping(path = "/plastic-card/{cardNo}/change-color/{color}")
    public ResponseEntity<String> changePlasticCardColor(@PathVariable String cardNo, @PathVariable String color) {
        PlasticCard plasticCard = this.plasticCardService.findByCardNo(cardNo);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(PLASTIC_CARD_NOT_FOUND);
        }
        plasticCard.setColor(color);
        this.plasticCardService.save(plasticCard);
        return ResponseEntity.ok("Color modificado");
    }

    @PatchMapping(path = "/plastic-card/{cardNo}/change-image/{imageURL}")
    public ResponseEntity<String> changePlasticCardImage(@PathVariable String cardNo, @PathVariable String imageURL) {
        PlasticCard plasticCard = this.plasticCardService.findByCardNo(cardNo);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(PLASTIC_CARD_NOT_FOUND);
        }
        plasticCard.setImageURL(imageURL);
        this.plasticCardService.save(plasticCard);
        return ResponseEntity.ok("Imagen modificada");
    }

    @DeleteMapping(path = "/plastic-card/delete/{cardNo}")
    public ResponseEntity<String> deletePlasticCard(@PathVariable String cardNo) {
        PlasticCard plasticCard = this.plasticCardService.findByCardNo(cardNo);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(PLASTIC_CARD_NOT_FOUND);
        }
        this.plasticCardService.deleteByCardNo(cardNo);
        return ResponseEntity.ok("Registro eliminado");
    }

}
