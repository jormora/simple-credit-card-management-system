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
    private String cardNo;

    private String userId;

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
