package com.example.bank.service;

import com.example.bank.model.CreditCard;
import com.example.bank.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }
    public List<CreditCard> findAll(){
        return creditCardRepository.findAll();
    }
}
