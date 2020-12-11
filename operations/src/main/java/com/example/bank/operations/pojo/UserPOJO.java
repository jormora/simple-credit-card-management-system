package com.example.bank.operations.pojo;

import com.example.bank.operations.model.User;

import java.io.Serializable;

public class UserPOJO implements Serializable {

    private String username;

    private String firstname;

    private String lastname;

    private Long cardId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public static User mapRequestEntity(UserPOJO userPOJO) {
        User user = new User();
        user.setUsername(userPOJO.getUsername());
        user.setFirstname(userPOJO.getFirstname());
        user.setLastname(userPOJO.getLastname());
        user.setCardId(userPOJO.getCardId());
        return user;
    }

    public static boolean isRequestEntityRight(UserPOJO userPOJO) {
        if (userPOJO.getUsername() == null || userPOJO.getFirstname() == null ||
            userPOJO.getLastname() == null || userPOJO.getCardId() == null) {
            return false;
        }
        if (userPOJO.getUsername().isEmpty() || userPOJO.getFirstname().isEmpty() ||
            userPOJO.getLastname().isEmpty()) {
            return false;
        }
        if (userPOJO.getCardId() <= 0L) return false;
        return true;
    }

}
