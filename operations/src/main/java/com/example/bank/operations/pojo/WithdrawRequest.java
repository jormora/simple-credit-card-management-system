package com.example.bank.operations.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawRequest implements Serializable {
    private BigDecimal amount;

    public WithdrawRequest() {

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
