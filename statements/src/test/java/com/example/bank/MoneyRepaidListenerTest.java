package com.example.bank;

import com.example.bank.statements.StatementsApplication;
import com.example.bank.statements.model.Statement;
import com.example.bank.statements.payload.request.MoneyRepaid;
import com.example.bank.statements.payload.request.Withdrawal;
import com.example.bank.statements.service.StatementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MimeTypeUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = StatementsApplication.class)
public class MoneyRepaidListenerTest {
    private static final String ANY_CARD_NR = "nr";

    @Autowired
    Sink sink;
    @Autowired
    StatementService statementService;

    @Test
    public void should_close_the_statement_when_money_repaid_event_happens() {
        Statement statement = new Statement();
        statement.setCardNr(ANY_CARD_NR);
        statement.setAmount(BigDecimal.valueOf(10000L));
        statement.setClosed(false);
        this.statementService.save(statement);

        // when
        sink.input().send(MessageBuilder.withPayload(new MoneyRepaid(ANY_CARD_NR, TEN))
            .setHeader("type", "close")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());

        // then
        assertThat(statementService
                .findLastByCardNr(ANY_CARD_NR).isClosed()).isTrue();
    }

}
