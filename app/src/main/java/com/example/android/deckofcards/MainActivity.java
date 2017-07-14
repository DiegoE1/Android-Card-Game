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


public class MainActivity extends AppCompatActivity {

    private static final String shuffleCardsEndPoint = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
    private String drawCardsEndPoint = "https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/?count=2";
    private String updatedDrawCardsEndPoint;

    private Button drawCards;

    private TextView cardNameOne;
    private TextView cardNameTwo;

    private ImageView cardPicOne;
    private ImageView cardPicTwo;

    private String deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawCards = (Button) findViewById(R.id.button_draw_two_cards);

        cardNameOne = (TextView) findViewById(R.id.tv_card_one);
        cardNameTwo = (TextView) findViewById(R.id.tv_card_two);

        cardPicOne = (ImageView) findViewById(R.id.image_card_one);
        cardPicTwo = (ImageView) findViewById(R.id.image_card_two);

        new ShuffleCardsTask().execute();

        drawCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNameOne.setVisibility(View.VISIBLE);
                cardNameTwo.setVisibility(View.VISIBLE);
                new DrawTwoCardsTask().execute();
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

                //TODO: return an array of Card

                //TODO: use the image url inside the card to load the image into an image view via glide

                arrayOfCards = wholeJson.getJSONArray("cards");

                for(int i = 0; i < arrayOfCards.length(); i++){
                    //todo: use the card object to store the values coming back from each card in the array of cards

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

                //TODO: remove strings since we are using the card object

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
                    }
                    if(i == 1) {
                        cardNameTwo.setText(cardArray.get(i).getValue() + " of " + cardArray.get(i).getSuit());
                        Glide.with(MainActivity.this).load(cardArray.get(i).getImage()).into(cardPicTwo);
                    }

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
