package com.example.bank.repository;

import com.example.bank.model.Statement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends CrudRepository<Statement, String> {
    //revisar
    @Query("select s from Statement s order by s.id ")
    Statement findLastByCardNr(String cardNr);

}
