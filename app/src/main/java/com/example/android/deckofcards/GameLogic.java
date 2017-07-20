package com.example.android.deckofcards;

/**
 * Created by diegoespinosa on 7/20/17.
 */

public class GameLogic {

    private static final int TOTAL_CARDS = 14;

    public static int getCardValue (String cardValue){
        for (int i = 0; i < TOTAL_CARDS; i++) {

            if (cardValue.equals("2")) {
                return 2;
            } else if (cardValue.equals("3")) {
                return 3;
            } else if (cardValue.equals("4")) {
                return 4;
            } else if (cardValue.equals("5")) {
                return 5;
            } else if (cardValue.equals("6")) {
                return 6;
            } else if (cardValue.equals("7")) {
                return 7;
            } else if (cardValue.equals("8")) {
                return 8;
            } else if (cardValue.equals("9")) {
                return 9;
            } else if (cardValue.equals("10")) {
                return 10;
            } else if (cardValue.equals("JACK") || cardValue.equals("KNAVE")) {
                return 11;
            } else if (cardValue.equals("QUEEN")) {
                return 12;
            } else if (cardValue.equals("KING")) {
                return 13;
            } else if (cardValue.equals("ACE")) {
                return 14;
            }
        }
        return 0;
    }

    public enum GameStatus {
        WINNER, LOSER, DRAW
    }

    public static GameStatus compareCards(String cardOne, String cardTwo) {
        int cardOneInt = getCardValue(cardOne);
        int cardTwoInt = getCardValue(cardTwo);

        if(cardOneInt == cardTwoInt){
            return GameStatus.DRAW;
        } else if(cardOneInt > cardTwoInt){
            return GameStatus.WINNER;
        } else {
            return GameStatus.LOSER;
        }
    }
}
