package com.example.bank.operations.pojo;

import java.math.BigDecimal;

public class RepaymentRequest {

    private BigDecimal amount;

    RepaymentRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
