package com.example.bank.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class WithdrawRequest {
    private BigDecimal amount;

    public WithdrawRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
