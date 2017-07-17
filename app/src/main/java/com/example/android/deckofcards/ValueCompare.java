package com.example.android.deckofcards;

/**
 * Created by diegoespinosa on 7/17/17.
 */

public class ValueCompare {
    String firstValue;
    String secondValue;

    public ValueCompare(String mValue, String nValue) {
        firstValue = mValue;
        secondValue = nValue;
    }

    int firstIntValue;
    int secondIntValue;
    int totalCards = 14;

    public int getFirstStringValue(String firstValue) {
        for (int i = 0; i < totalCards; i++) {

            if (firstValue.equals("2")) {
                firstIntValue = 2;
            } else if (firstValue.equals("3")) {
                firstIntValue = 3;
            } else if (firstValue.equals("4")) {
                firstIntValue = 4;
            } else if (firstValue.equals("5")) {
                firstIntValue = 5;
            } else if (firstValue.equals("6")) {
                firstIntValue = 6;
            } else if (firstValue.equals("7")) {
                firstIntValue = 7;
            } else if (firstValue.equals("8")) {
                firstIntValue = 8;
            } else if (firstValue.equals("9")) {
                firstIntValue = 9;
            } else if (firstValue.equals("10")) {
                firstIntValue = 10;
            } else if (firstValue.equals("JACK") || firstValue.equals("KNAVE")) {
                firstIntValue = 11;
            } else if (firstValue.equals("QUEEN")) {
                firstIntValue = 12;
            } else if (firstValue.equals("KING")) {
                firstIntValue = 13;
            } else if (firstValue.equals("ACE")) {
                firstIntValue = 14;
            }
        }
        return firstIntValue;
    }

    public int getSecondStringValue(String secondValue) {
        for (int i = 0; i < totalCards; i++) {

            if (secondValue.equals("2")) {
                secondIntValue = 2;
            } else if (secondValue.equals("3")) {
                secondIntValue = 3;
            } else if (secondValue.equals("4")) {
                secondIntValue = 4;
            } else if (secondValue.equals("5")) {
                firstIntValue = 5;
            } else if (secondValue.equals("6")) {
                secondIntValue = 6;
            } else if (secondValue.equals("7")) {
                secondIntValue = 7;
            } else if (secondValue.equals("8")) {
                secondIntValue = 8;
            } else if (secondValue.equals("9")) {
                secondIntValue = 9;
            } else if (secondValue.equals("10")) {
                secondIntValue = 10;
            } else if (secondValue.equals("JACK") || secondValue.equals("KNAVE")) {
                secondIntValue = 11;
            } else if (secondValue.equals("QUEEN")) {
                secondIntValue = 12;
            } else if (secondValue.equals("KING")) {
                secondIntValue = 13;
            } else if (secondValue.equals("ACE")) {
                secondIntValue = 14;
            }
        }
        return secondIntValue;
    }

}
