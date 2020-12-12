package com.example.bank.cards.service;

import com.example.bank.cards.model.PlasticCard;
import com.example.bank.cards.repository.PlasticCardRepository;
import org.springframework.stereotype.Service;

@Service
public class PlasticCardService {

    private final PlasticCardRepository plasticCardRepository;

    public PlasticCardService(PlasticCardRepository plasticCardRepository) {
        this.plasticCardRepository = plasticCardRepository;
    }

    public PlasticCard findByCardNo(String cardNo) {
        return this.plasticCardRepository.findById(cardNo).orElse(null);
    }

    public PlasticCard save(PlasticCard plasticCard) {
        return this.plasticCardRepository.save(plasticCard);
    }

    public void deleteByCardNo(String cardNo) {
        this.plasticCardRepository.deleteById(cardNo);
    }
}
