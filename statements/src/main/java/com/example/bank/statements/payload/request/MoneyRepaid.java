package com.example.bank.statements.payload.request;

import java.math.BigDecimal;

public class MoneyRepaid {
    private String cardNo;
    private BigDecimal amount;

    public MoneyRepaid() {

    }

    public MoneyRepaid(String cardNo, BigDecimal amount) {
        this.cardNo = cardNo;
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
