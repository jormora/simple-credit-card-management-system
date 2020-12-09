package com.example.bank.service;

import com.example.bank.model.PlasticCard;
import com.example.bank.repository.PlasticCardRepository;
import org.springframework.stereotype.Service;

@Service
public class PlasticCardService {

    private final PlasticCardRepository plasticCardRepository;

    public PlasticCardService(PlasticCardRepository plasticCardRepository) {
        this.plasticCardRepository = plasticCardRepository;
    }

    public PlasticCard findById(Integer id) {
        return this.plasticCardRepository.findById(id).orElse(null);
    }

    public PlasticCard save(PlasticCard plasticCard) {
        return this.plasticCardRepository.save(plasticCard);
    }

    public void deleteById(Integer id) {
        this.plasticCardRepository.deleteById(id);
    }
}
