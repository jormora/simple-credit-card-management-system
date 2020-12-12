package com.example.bank.operations.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class RepaymentRequest implements Serializable {

    private BigDecimal amount;

    RepaymentRequest() {

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
