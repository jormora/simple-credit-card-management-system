package com.example.bank.operations.pojo;

import com.example.bank.operations.model.CreditCard;

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

    public BigDecimal getInitialLimit() {
        return initialLimit;
    }

    public void setInitialLimit(BigDecimal initialLimit) {
        this.initialLimit = initialLimit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static CreditCard mapRequestEntity(CreditCardPOJO creditCardPOJO) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNo(creditCardPOJO.getCardNo());
        creditCard.setUserId(creditCardPOJO.getUserId());
        creditCard.setInitialLimit(creditCardPOJO.getInitialLimit());
        creditCard.setUsedLimit(BigDecimal.ZERO);
        return creditCard;
    }

    public static boolean isRequestEntityRight(CreditCardPOJO creditCardPOJO) {
        if (creditCardPOJO.getCardNo() == null || creditCardPOJO.getInitialLimit() == null ||
            creditCardPOJO.getUserId() == null) {
            return false;
        }
        return creditCardPOJO.getInitialLimit().compareTo(BigDecimal.ZERO) >= 0;
    }

}
