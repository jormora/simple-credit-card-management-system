package com.example.bank.statements.payload.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


public class Withdrawal implements Serializable {

    private Long id;
    private String cardNo;
    private BigDecimal amount;
    private OffsetDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
