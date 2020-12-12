package com.example.bank.users.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreditCardPOJO implements Serializable {

    private String cardNo;

    private BigDecimal initialLimit;

    private String userId;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getInitialLimit() {
        return initialLimit;
    }

    public void setInitialLimit(BigDecimal initialLimit) {
        this.initialLimit = initialLimit;
    }

}
