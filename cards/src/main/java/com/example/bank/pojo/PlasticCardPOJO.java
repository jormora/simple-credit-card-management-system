package com.example.bank.pojo;

import com.example.bank.model.PlasticCard;
import org.h2.util.StringUtils;

public class PlasticCardPOJO extends PlasticCard {

    public static PlasticCard mapRequestEntity(PlasticCardPOJO plasticCardPOJO) {
        PlasticCard plasticCard = new PlasticCard();
        plasticCard.setId(plasticCardPOJO.getId());
        plasticCard.setCode(plasticCardPOJO.getCode());
        plasticCard.setPassword(plasticCardPOJO.getPassword());
        plasticCard.setColor(plasticCardPOJO.getColor());
        plasticCard.setImageURL(plasticCardPOJO.getImageURL());
        plasticCard.setOwnerName(plasticCardPOJO.getOwnerName());
        plasticCard.setUsername(plasticCardPOJO.getUsername());
        plasticCard.setExpirationDate(plasticCardPOJO.getExpirationDate());
        return plasticCard;
    }

    public static boolean isRequestEntityRight(PlasticCardPOJO plasticCardPOJO) {
        if (plasticCardPOJO.getId() == null || plasticCardPOJO.getCode() == null ||
            plasticCardPOJO.getPassword() == null || plasticCardPOJO.getOwnerName() == null ||
            plasticCardPOJO.getColor() == null || plasticCardPOJO.getExpirationDate() == null ||
            plasticCardPOJO.getImageURL() == null || plasticCardPOJO.getUsername() == null) {
            return false;
        }
        if (plasticCardPOJO.getId() <= 0 || plasticCardPOJO.getCode() < 0) {
            return false;
        }
        if (plasticCardPOJO.getPassword().length() != 4) {
            return false;
        } else {
            for (int i = 0; i < plasticCardPOJO.getPassword().length(); i++) {
                if (!StringUtils.isNumber(String.valueOf(plasticCardPOJO.getPassword().charAt(i)))) {
                    return false;
                }
            }
        }
        return true;
    }

}
