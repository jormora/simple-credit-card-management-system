package com.example.bank.cards.pojo;

import com.example.bank.cards.model.PlasticCard;

public class PlasticCardPOJO extends PlasticCard {

    public static PlasticCard mapRequestEntity(PlasticCardPOJO plasticCardPOJO) {
        PlasticCard plasticCard = new PlasticCard();
        plasticCard.setCardNo(plasticCardPOJO.getCardNo());
        plasticCard.setPin(plasticCardPOJO.getPin());
        plasticCard.setUserId(plasticCardPOJO.getUserId());
        plasticCard.setColor(plasticCardPOJO.getColor());
        plasticCard.setImageURL(plasticCardPOJO.getImageURL());
        plasticCard.setExpirationDate(plasticCardPOJO.getExpirationDate());
        return plasticCard;
    }

    public static boolean isRequestEntityRight(PlasticCardPOJO plasticCardPOJO) {
        if (plasticCardPOJO.getCardNo() == null || plasticCardPOJO.getPin() == null ||
            plasticCardPOJO.getUserId() == null || plasticCardPOJO.getColor() == null ||
            plasticCardPOJO.getImageURL() == null || plasticCardPOJO.getExpirationDate() == null) {
            return false;
        }
        if (plasticCardPOJO.getPin() < 0) {
            return false;
        }
        return plasticCardPOJO.getPin() >= 1000 && plasticCardPOJO.getPin() <= 9999;
    }

}
