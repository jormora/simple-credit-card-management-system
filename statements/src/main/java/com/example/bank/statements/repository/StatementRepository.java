package com.example.bank.statements.repository;

import com.example.bank.statements.model.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends CrudRepository<Statement, Long> {
    Statement findFirstByCardNrOrderByIdDesc(String cardNr);

    List<Statement> findByClosedAndCardNr(boolean closed, String cardNr);

}
