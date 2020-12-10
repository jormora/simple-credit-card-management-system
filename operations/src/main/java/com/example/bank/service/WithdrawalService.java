package com.example.bank.service;

import com.example.bank.model.CreditCard;
import com.example.bank.model.Withdrawal;
import com.example.bank.repository.WithdrawalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;

    public WithdrawalService(WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    public List<Withdrawal> findAllByCreditCard(CreditCard creditCard) {
        return (List<Withdrawal>) this.withdrawalRepository.findAllByCreditCardW(creditCard);
    }

    public Withdrawal save(Withdrawal withdrawal) {
        return this.withdrawalRepository.save(withdrawal);
    }

    public void deleteAllByCreditCard(CreditCard creditCard) {
        this.withdrawalRepository.deleteAllByCreditCardW(creditCard);
    }

}
