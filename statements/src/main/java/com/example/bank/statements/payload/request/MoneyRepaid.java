package com.example.bank.statements.payload.request;

import java.math.BigDecimal;

public class MoneyRepaid {
    private final String cardNo;
    private final BigDecimal amount;

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
