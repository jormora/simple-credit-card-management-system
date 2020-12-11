package com.example.bank.pojo;

import com.example.bank.model.CreditCard;
import com.example.bank.model.User;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreditCardPOJO implements Serializable {

    private Long id;

    private BigDecimal initialLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInitialLimit() {
        return initialLimit;
    }

    public void setInitialLimit(BigDecimal initialLimit) {
        this.initialLimit = initialLimit;
    }

    public static CreditCard mapRequestEntity(CreditCardPOJO creditCardPOJO, User user) {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(creditCardPOJO.getId());
        creditCard.setUser(user);
        creditCard.setInitialLimit(creditCardPOJO.getInitialLimit());
        creditCard.setUsedLimit(0L);
        return creditCard;
    }

    public static boolean isRequestEntityRight(CreditCardPOJO creditCardPOJO) {
        if (creditCardPOJO.getId() == null || creditCardPOJO.getInitialLimit() == null) {
            return false;
        }
        if (creditCardPOJO.getId() <= 0L || creditCardPOJO.getInitialLimit() <= 0L) return false;
        return true;
    }

}
