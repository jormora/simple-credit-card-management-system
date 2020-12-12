package com.example.bank.operations.service;

import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCard> findAll() {
        return (List<CreditCard>) this.creditCardRepository.findAll();
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
