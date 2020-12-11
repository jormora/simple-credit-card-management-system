package com.example.bank.repository;

import com.example.bank.model.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, String> {

    List<CreditCard> findAll();
}
