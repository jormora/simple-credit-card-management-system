package com.example.bank.repository;

import com.example.bank.model.CreditCard;
import com.example.bank.model.Withdrawal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WithdrawalRepository extends CrudRepository<Withdrawal, Long> {
    Iterable<Withdrawal> findAllByCreditCardW(CreditCard creditCard);

    @Transactional
    void deleteAllByCreditCardW(CreditCard creditCard);
}
