package com.example.bank.cards.repository;

import com.example.bank.cards.model.PlasticCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlasticCardRepository extends CrudRepository<PlasticCard, String> {

}
