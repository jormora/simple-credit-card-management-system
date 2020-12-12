package com.example.bank.users.pojo;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class PlasticCardPOJO implements Serializable {

    private String cardNo;

    private Integer pin;

    private String userId;

    private String color;

    private String imageURL;

    private OffsetDateTime expirationDate;

    public PlasticCardPOJO() {

    }

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
