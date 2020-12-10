package com.example.bank;

import com.example.bank.payload.request.MoneyRepaid;
import com.example.bank.repository.StatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MoneyRepaidListenerTest {
    private static final String ANY_CARD_NR = "nr";

    @Autowired
    Sink sink;
    @Autowired
    StatementRepository statementRepository;

    @Test
    public void should_close_the_statement_when_money_repaid_event_happens() {
        // when
        sink.input()
                .send(new GenericMessage<>(new MoneyRepaid(ANY_CARD_NR, TEN)));

        // then
        assertThat(statementRepository
                .findLastByCardNr(ANY_CARD_NR).isClosed()).isTrue();
    }
}
