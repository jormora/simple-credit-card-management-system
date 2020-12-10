package com.example.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "withdrawal")
public class Withdrawal implements Serializable {

    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "credit_card")
    private CreditCard creditCardW;

    private Long amount;

    @Column(name = "date_time", columnDefinition = "DATETIME")
    private OffsetDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCard getCreditCardW() {
        return creditCardW;
    }

    public void setCreditCardW(CreditCard creditCardW) {
        this.creditCardW = creditCardW;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
