package com.example.bank.service;

import com.example.bank.model.Statement;
import com.example.bank.repository.StatementRepository;
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
