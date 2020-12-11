package com.example.bank.operations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "credit_card")
public class CreditCard implements Serializable {

    @Id
    private String CardNo;

    @JsonIgnore
    @OneToOne(mappedBy = "creditCard")
    private User user;

    private BigDecimal initialLimit;

    private BigDecimal usedLimit;

    @JsonIgnore
    @OneToMany(mappedBy = "creditCardW")
    private Set<Withdrawal> withdrawals;

    public boolean isThereEnoughMoneyToWithdraw(BigDecimal amount) {
        BigDecimal available = this.initialLimit.subtract(this.usedLimit);
        return available.compareTo(amount) >= 0;
    }

    public boolean checkAmountIsLessThanUsedLimit(BigDecimal amount) {
        return this.usedLimit.compareTo(amount) > 0;
    }

    public void withdraw(BigDecimal amount) {
        this.usedLimit = this.usedLimit.add(amount);
    }

    public void repay(BigDecimal amount) {
        this.usedLimit = this.usedLimit.subtract(amount);
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getInitialLimit() {
        return initialLimit;
    }

    public void setInitialLimit(BigDecimal initialLimit) {
        this.initialLimit = initialLimit;
    }

    public BigDecimal getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(BigDecimal usedLimit) {
        this.usedLimit = usedLimit;
    }

    public Set<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(Set<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
    }

}
