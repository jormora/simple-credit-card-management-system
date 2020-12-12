package com.example.bank.statements.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Statement {
    @Id
    @GeneratedValue
    private Long id;
    private String cardNr;
    private BigDecimal amount;
    private boolean closed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNr() {
        return cardNr;
    }

    public void setCardNr(String cardNr) {
        this.cardNr = cardNr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
