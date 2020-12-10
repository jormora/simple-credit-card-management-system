package com.example.bank.payload.request;

import java.math.BigDecimal;

public class MoneyRepaid {
    final String cardNo;
    final BigDecimal amount;

    public MoneyRepaid(String cardNo, BigDecimal amount) {
        this.cardNo = cardNo;
        this.amount = amount;
    }
}
