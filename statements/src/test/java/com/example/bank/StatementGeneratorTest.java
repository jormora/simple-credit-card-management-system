package com.example.bank;

import com.example.bank.statements.StatementsApplication;
import com.example.bank.statements.generator.StatementGenerator;
import com.example.bank.statements.service.StatementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = StatementsApplication.class)
public class StatementGeneratorTest {
    private static final String USED_CARD = "123";
    private static final String NOT_USED_CARD = "456";

    @Autowired
    StatementGenerator statementGenerator;
    @Autowired
    StatementService statementService;

//    @Test
//    public void should_create_statement_only_if_there_are_withdrawals() {
//        // when
//        statementGenerator.generateStatements();
//
//        // then
//        assertThat(statementService
//                .findByClosedAndCardNr(false,USED_CARD),hasSize(0));
//        assertThat(statementService
//                .findByClosedAndCardNr(false,NOT_USED_CARD),hasSize(0));
//
//    }
}
