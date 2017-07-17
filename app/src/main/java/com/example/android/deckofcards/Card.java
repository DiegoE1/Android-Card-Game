package com.example.android.deckofcards;

/**
 * Created by diegoespinosa on 7/13/17.
 */

public class Card {
    String image;
    String value;
    String suit;
    String code;

    public Card(String mImage, String mValue, String mSuit, String mCode){
        image = mImage;
        value = mValue;
        suit = mSuit;
        code = mCode;
    }

    public Card() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Card{" +
                "image='" + image + '\'' +
                ", value='" + value + '\'' +
                ", suit='" + suit + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
