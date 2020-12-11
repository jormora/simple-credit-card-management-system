package com.example.bank.statements.service;

import com.example.bank.statements.model.CreditCard;
import com.example.bank.statements.repository.CreditCardRepository;
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
