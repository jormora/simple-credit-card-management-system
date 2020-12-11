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

    public CreditCard findByCardNo(String cardNo) {
        return this.creditCardRepository.findById(cardNo).orElse(null);
    }

    public CreditCard save(CreditCard creditCard) {
        return this.creditCardRepository.save(creditCard);
    }

    public void deleteByCardNo(String cardNo) {
        this.creditCardRepository.deleteById(cardNo);
    }

}
