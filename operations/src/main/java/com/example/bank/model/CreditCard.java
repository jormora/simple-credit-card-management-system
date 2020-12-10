package com.example.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "credit_card")
public class CreditCard implements Serializable {

    @Id
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "creditCard")
    private User user;

    private Long initialLimit;

    private Long usedLimit;

    @JsonIgnore
    @OneToMany(mappedBy = "creditCardW")
    private Set<Withdrawal> withdrawals;

    public boolean isThereEnoughMoneyToWithdraw(Long amount) {
        Long available = this.initialLimit - this.usedLimit;
        return available.compareTo(amount) >= 0;
    }

    public boolean checkAmountIsLessThanUsedLimit(Long amount) {
        return this.usedLimit >= amount;
    }

    public void withdraw(Long amount) {
        this.usedLimit = this.usedLimit + amount;
    }

    public void chargeBack(Long amount) {
        this.usedLimit = this.usedLimit - amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Set<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(Set<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
    }

}
