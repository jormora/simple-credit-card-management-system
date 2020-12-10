package com.example.bank.service;

import com.example.bank.model.CreditCard;
import com.example.bank.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public CreditCard findById(Long id) {
        return this.creditCardRepository.findById(id).orElse(null);
    }

    public CreditCard save(CreditCard creditCard) {
        return this.creditCardRepository.save(creditCard);
    }

    public void deleteById(Long id) {
        this.creditCardRepository.deleteById(id);
    }

}
