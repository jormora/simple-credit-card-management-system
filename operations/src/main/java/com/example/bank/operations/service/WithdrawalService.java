package com.example.bank.operations.service;

import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.model.Withdrawal;
import com.example.bank.operations.repository.WithdrawalRepository;
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
