package com.arnel.wacht8.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.arnel.wacht8.R;
import com.arnel.wacht8.model.Deck;
import com.arnel.wacht8.services.MyService;
import com.arnel.wacht8.utils.NetworkHelper;

import java.util.LinkedList;

import retrofit2.Retrofit;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG=MenuActivity.class.getSimpleName();
    public static final String BASE_URL="https://deckofcardsapi.com/api/";
    private static Retrofit retrofit=null;
    private Deck deck;
    private RecyclerView recyclerView =null;
    private Boolean networkOk;
    private LinkedList<BroadcastReceiver> receivers=new LinkedList<>();
    private BroadcastReceiver brNewDeck =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Als deck niet null is werd er al een deck gemaakt
            //en mag de methode niet opnieuw worden uitgevoerd.
            if(deck==null) {
                deck = (Deck) intent.getParcelableExtra(MyService.MY_SERVICE_PAYLOAD);
                Toast.makeText(MenuActivity.this, "Deck gemaakt. " + deck.getDeckID(), Toast.LENGTH_SHORT).show();

                registerReceiver(brDrawCardsFromDeck, MyService.MES_DRAW_CARDS_FROM_DECK);
                MyService.drawCardsFromDeck(MenuActivity.this,deck.getDeckID(),2);
            }
        }
    };

    private BroadcastReceiver brDrawCardsFromDeck =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            deck=(Deck) intent.getParcelableExtra(MyService.MY_SERVICE_PAYLOAD);
            Toast.makeText(MenuActivity.this,"Deck gemaakt. "+deck.getDeckID(),Toast.LENGTH_SHORT).show();


        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        registerReceiver(brNewDeck,MyService.MES_NEW_DECK);

        networkOk = NetworkHelper.hasNetworkAccess(this);
        if (networkOk) {
            MyService.getNewDeck(this);
        } else {
            Toast.makeText(this, "Netwerk is niet beschikbaar", Toast.LENGTH_LONG).show();
        }







    }

    private void registerReceiver(BroadcastReceiver broadcastReceiver,String intentfilter){
        MyService.registerReceiver(getApplicationContext(),broadcastReceiver,intentfilter);
        receivers.add(broadcastReceiver);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyService.unregisterReceiver(getApplicationContext(),receivers);
    }






}
