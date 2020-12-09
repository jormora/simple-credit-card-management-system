package com.example.bank.controller;

import com.example.bank.model.PlasticCard;
import com.example.bank.service.PlasticCardService;
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

    @GetMapping(path = "/plastic-card/{id}")
    public ResponseEntity<PlasticCard> getPlasticCard(@PathVariable Integer id) {
        PlasticCard plasticCard = this.plasticCardService.findById(id);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(plasticCard);
    }

    @PatchMapping(path = "/plastic-card/{id}/change-color/{color}")
    public ResponseEntity<String> changePlasticCardColor(@PathVariable Integer id, @PathVariable String color) {
        PlasticCard plasticCard = this.plasticCardService.findById(id);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(PLASTIC_CARD_NOT_FOUND);
        }
        plasticCard.setColor(color);
        this.plasticCardService.save(plasticCard);
        return ResponseEntity.ok("Color modificado");
    }

    @PatchMapping(path = "/plastic-card/{id}/change-image/{imageURL}")
    public ResponseEntity<String> changePlasticCardImage(@PathVariable Integer id, @PathVariable String imageURL) {
        PlasticCard plasticCard = this.plasticCardService.findById(id);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(PLASTIC_CARD_NOT_FOUND);
        }
        plasticCard.setImageURL(imageURL);
        this.plasticCardService.save(plasticCard);
        return ResponseEntity.ok("Imagen modificada");
    }

    @DeleteMapping(path = "/plastic-card/delete/{id}")
    public ResponseEntity<String> deletePlasticCard(@PathVariable Integer id) {
        PlasticCard plasticCard = this.plasticCardService.findById(id);
        if (plasticCard == null) {
            LOGGER.info(PLASTIC_CARD_NOT_FOUND);
            return ResponseEntity.badRequest().body(PLASTIC_CARD_NOT_FOUND);
        }
        this.plasticCardService.deleteById(id);
        return ResponseEntity.ok("Registro eliminado");
    }

}
