package com.example.bank.cards.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "plastic_card")
public class PlasticCard implements Serializable {

    @Id
    private String cardNo;

    private Integer pin;

    private String userId;

    private String color;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "expiration_date", columnDefinition = "DATETIME")
    private OffsetDateTime expirationDate;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

}
