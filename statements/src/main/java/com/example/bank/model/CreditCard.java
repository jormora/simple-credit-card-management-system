package com.example.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class CreditCard {
    @Id
    private Long id;

    private String user;

    private Long initialLimit;

    private Long usedLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getInitialLimit() {
        return initialLimit;
    }

    public void setInitialLimit(Long initialLimit) {
        this.initialLimit = initialLimit;
    }

    public Long getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(Long usedLimit) {
        this.usedLimit = usedLimit;
    }
}
