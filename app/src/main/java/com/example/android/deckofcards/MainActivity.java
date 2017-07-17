package com.example.android.deckofcards;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*

    todo: (1) Finish Logic for the card game
     - User win vs lose
    todo: (2) Add an Indeterminate Progress Bar
     -Show when the async task starts
     -Hide when the async task finishes
     -It should be in the center of the screen
    todo: (3) Add tracking for the Number of games played
     -Show how many games in total have been played
     -Show how many games the user won
     -Show the users Win percentage
    todo: (4) Create a repo for the chuck norris jokes app
     -Push your code to that repo
     -Will probably need to research the steps

 */

// Card Values: 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack or Knave (J), Queen (Q), King (K), Ace (A)


public class MainActivity extends AppCompatActivity {

    private static final String shuffleCardsEndPoint = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
    private String drawCardsEndPoint = "https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/?count=2";
    private String updatedDrawCardsEndPoint;

    private Button drawCards;

    private TextView playerUser;
    private TextView playerComp;

    private TextView cardNameOne;
    private TextView cardNameTwo;

    private TextView cardValueOne;
    private TextView cardValueTwo;

    private TextView cardGameWhoWon;
    private TextView cardGameWinner;

    private ImageView cardPicOne;
    private ImageView cardPicTwo;

    private String deckId;
    private String cardStringValueTag;
    private String gameStringWhoWon;
    private String gameStringWinner;

    private String gameStringValueOne;
    private String gameStringValueTwo;

    private String gameStringOutput;
    private String gameStringWin;
    private String gameStringLose;
    private String gameStringTie;

    private int valueOne;
    private int valueTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawCards = (Button) findViewById(R.id.button_draw_two_cards);

        playerUser = (TextView) findViewById(R.id.tv_player_user);
        playerComp = (TextView) findViewById(R.id.tv_player_computer);

        cardNameOne = (TextView) findViewById(R.id.tv_card_one);
        cardNameTwo = (TextView) findViewById(R.id.tv_card_two);

        cardValueOne = (TextView) findViewById(R.id.tv_user_value);
        cardValueTwo = (TextView) findViewById(R.id.tv_computer_value);

        cardGameWhoWon = (TextView) findViewById(R.id.tv_game_who_won);
        cardGameWinner = (TextView) findViewById(R.id.tv_game_winner);

        cardPicOne = (ImageView) findViewById(R.id.image_card_one);
        cardPicTwo = (ImageView) findViewById(R.id.image_card_two);

        cardStringValueTag = getString(R.string.card_value_tag);
        gameStringWhoWon = getString(R.string.game_who_won);
        gameStringWinner = getString(R.string.game_winner);

        gameStringWin = getString(R.string.game_user_won);
        gameStringLose = getString(R.string.game_computer_won);
        gameStringTie = getString(R.string.game_tie);

        new ShuffleCardsTask().execute();

        drawCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DrawTwoCardsTask().execute();
                ///*
                playerUser.setVisibility(View.VISIBLE);
                playerComp.setVisibility(View.VISIBLE);
                cardNameOne.setVisibility(View.VISIBLE);
                cardNameTwo.setVisibility(View.VISIBLE);
                cardValueOne.setVisibility(View.VISIBLE);
                cardValueTwo.setVisibility(View.VISIBLE);
                cardGameWhoWon.setVisibility(View.VISIBLE);
                cardGameWinner.setVisibility(View.VISIBLE);
                //*/
            }
        });
    }

    public class ShuffleCardsTask extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(shuffleCardsEndPoint).build();
            String cardResponse = null;
            try {
                Response response = client.newCall(request).execute();
                cardResponse = response.body().string();
                Log.d("cardResponse", cardResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String deckId = null;
            try{
                JSONObject jsonObject = new JSONObject(cardResponse);
                Log.d("TAG", "jsonObject " + jsonObject.getString("deck_id"));
                deckId = jsonObject.getString("deck_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return deckId;
        }

        @Override
        protected void onPostExecute(String deckId) {
            if(deckId != null){
                Log.d("postExecString", deckId);
                updatedDrawCardsEndPoint = "https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count=2";
                Log.d("updatedDrawCardsEndPt", updatedDrawCardsEndPoint);

            }
        }
    }

    public class DrawTwoCardsTask extends AsyncTask<Void, Void, ArrayList <Card>> {
        @Override
        protected ArrayList <Card> doInBackground(Void... params) {
            OkHttpClient clientSecond = new OkHttpClient();
            Log.d("secondRequest", updatedDrawCardsEndPoint);
            Request requestSecond = new Request.Builder().url(updatedDrawCardsEndPoint).build();
            String cardReturn = null;

            try {
                Response responseSecond = clientSecond.newCall(requestSecond).execute();
                cardReturn = responseSecond.body().string();
                Log.d("cardReturn", cardReturn);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject wholeJson;
            JSONArray arrayOfCards;
            ArrayList <Card> cards = new ArrayList<Card>();

            try {
                wholeJson = new JSONObject(cardReturn);

                arrayOfCards = wholeJson.getJSONArray("cards");

                for(int i = 0; i < arrayOfCards.length(); i++){
                    JSONObject object = arrayOfCards.getJSONObject(i);
                    Card cardInArray = new Card();

                    cardInArray.setImage(object.getString("image"));
                    cardInArray.setValue(object.getString("value"));
                    cardInArray.setSuit(object.getString("suit"));
                    cardInArray.setCode(object.getString("code"));

                    cards.add(i, cardInArray);
                    Log.d("cardInArray", String.valueOf(cardInArray));
                    Log.d("cards list", cards.toString());
                    Log.d("card list array", String.valueOf(cards.get(i)));
                }

                Log.d("card one", String.valueOf(cards.get(0)));
                Log.d("card two", String.valueOf(cards.get(1)));

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cardArray) {
            if(cardArray != null){
                Log.d("post execute", String.valueOf(cardArray));
                cardPicOne.setVisibility(View.VISIBLE);
                cardPicTwo.setVisibility(View.VISIBLE);

                for(int i = 0; i < cardArray.size(); i++) {
                    if(i == 0) {
                        cardNameOne.setText(cardArray.get(i).getValue() + " of " + cardArray.get(i).getSuit());
                        Glide.with(MainActivity.this).load(cardArray.get(i).getImage()).into(cardPicOne);
                        cardValueOne.setText(cardStringValueTag + " " + cardArray.get(i).getValue());
                        gameStringValueOne = cardArray.get(i).getValue();
                    }
                    if(i == 1) {
                        cardNameTwo.setText(cardArray.get(i).getValue() + " of " + cardArray.get(i).getSuit());
                        Glide.with(MainActivity.this).load(cardArray.get(i).getImage()).into(cardPicTwo);
                        cardValueTwo.setText(cardStringValueTag + " " + cardArray.get(i).getValue());
                        gameStringValueTwo = cardArray.get(i).getValue();
                    }

                }
                cardGameWhoWon.setText(gameStringWhoWon);
                cardGameWinner.setText(gameStringWinner);

                ValueCompare cardValueCompare = new ValueCompare(gameStringValueOne, gameStringValueTwo);
                valueOne = cardValueCompare.getFirstStringValue(gameStringValueOne);
                valueTwo = cardValueCompare.getSecondStringValue(gameStringValueTwo);

                if(valueOne == valueTwo){
                    cardGameWinner.setText(gameStringWinner + " " + gameStringTie);
                }else if (valueOne > valueTwo){
                    cardGameWinner.setText(gameStringWinner + " " + gameStringWin);
                }else if (valueOne < valueTwo){
                    cardGameWinner.setText(gameStringWinner + " " + gameStringLose);
                }

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shuffler_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new ShuffleCardsTask().execute();
        String toaster = "Cards Shuffled";
        Toast toast = Toast.makeText(this, toaster, Toast.LENGTH_SHORT);
        toast.show();
        return super.onOptionsItemSelected(item);
    }
}
