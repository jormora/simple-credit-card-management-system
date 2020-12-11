package com.example.bank.repository;

import com.example.bank.model.Statement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends CrudRepository<Statement, String> {
    Statement findFirstByCardNrOrderByIdDesc(String cardNr);

    List<Statement> findByClosedAndCardNr(boolean closed, String cardNr);

}
