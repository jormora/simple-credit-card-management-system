package com.example.bank.repository;

import com.example.bank.model.PlasticCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlasticCardRepository extends CrudRepository<PlasticCard, Integer> {

}
