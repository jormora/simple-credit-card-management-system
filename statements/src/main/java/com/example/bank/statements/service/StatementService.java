package com.example.bank.statements.service;

import com.example.bank.statements.model.Statement;
import com.example.bank.statements.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementService {
    private final StatementRepository statementRepository;

    public StatementService(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }
    public Statement findLastByCardNr(String cardNr){
        return statementRepository.findFirstByCardNrOrderByIdDesc(cardNr);
    }
    public void save(Statement statement){
        statementRepository.save(statement);
    }
    public List<Statement> findByClosedAndCardNr(boolean closed, String cardNr){
        return statementRepository.findByClosedAndCardNr(closed,cardNr);
    }
}
