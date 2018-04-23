package com.arnel.wacht8.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.arnel.wacht8.model.Deck;
import com.arnel.wacht8.model.Pile;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;


/**
 * Created by arnel on 11/04/2018.
 */

public class MyService extends IntentService {

    public static final String TAG="MyService";
    public static final String MY_SERVICE_MESSAGE="myServiceMessage";
    public static final String MES_NEW_DECK ="newDeckMessage";
    public static final String MES_DRAW_CARDS_FROM_DECK="drawCardsFromDeckMessage";
    
    public static final String MY_SERVICE_PAYLOAD="myServicePayload";



    private static final String SR_GET_NEW_DECK="getNewDeck";
    private static final String SR_DRAW_CARDS_FROM_DECK="drawCardsFromDeck";

    private static final String PARAM_DECK_ID="deckID";

    private static final String PARAM_DRAW_CARDS_FROM_DECK_COUNT="count";



    private DeckApiService deckApiService;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyService() {
        super("MyService");
    }







    public static void registerReceiver(Context c,BroadcastReceiver broadcastReceiver,String intentfilter){
        LocalBroadcastManager.getInstance(c)
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(intentfilter));
    }

    public static void unregisterReceiver(Context c, List<BroadcastReceiver> broadcastReceivers){
        for (BroadcastReceiver b:broadcastReceivers) {
            LocalBroadcastManager.getInstance(c)
                    .unregisterReceiver(b);
        }
    }


    public static void getNewDeck(Context context) {
        Intent i=new Intent(context,MyService.class);
        i.setAction(SR_GET_NEW_DECK);
        context.startService(i);
    }

    public static void drawCardsFromDeck(Context context,String deckID,int count) {
        Intent i=new Intent(context,MyService.class);
        i.setAction(SR_DRAW_CARDS_FROM_DECK);
        i.putExtra(PARAM_DECK_ID,deckID);
        i.putExtra(PARAM_DRAW_CARDS_FROM_DECK_COUNT,count);
        context.startService(i);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Intent messageIntent=null;

        deckApiService=DeckApiService.retrofit.create(DeckApiService.class);


        if (intent != null) {

            Bundle b =intent.getExtras();
            final String action = intent.getAction();

            try {
                switch (action) {
                    case SR_GET_NEW_DECK:
                        //Geeft een nieuw deck terug, het deckid is op te vragen
                        messageIntent = handleGetNewDeck();
                        break;

                    case SR_DRAW_CARDS_FROM_DECK:
                        //Geeft een pile terug met getrokken kaarten uit een deck
                        //in de intent zijn deckID en het aantal te trekken kaarten meegegeven
                        messageIntent = handleDrawCardsFromDeck(intent);
                        break;

                    default:
                        Log.d(TAG, "onHandleIntent: Wrong parameters");
                        messageIntent = null;
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onHandleIntent: Wrong intent");
                messageIntent = null;
            }
        }


        if(messageIntent!=null) {
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
            manager.sendBroadcast(messageIntent);
        }

    }




    private Intent handleGetNewDeck(){
        Call<Deck> call=deckApiService.getNewDeck();

        Deck deck;

        try {
            deck=call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "onHandleIntent: "+e.getMessage());
            return null;
        }



        Intent messageIntent=new Intent(MES_NEW_DECK);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD,deck);

        return messageIntent;
    }


    private Intent handleDrawCardsFromDeck(Intent intent){


        String deckID=intent.getStringExtra(PARAM_DECK_ID);
        Integer count= intent.getIntExtra(PARAM_DRAW_CARDS_FROM_DECK_COUNT,0);

        if(deckID==null || count==0){
            Log.d(TAG, "drawCardsFromDeck: Wrong parameters");
            return null;
        }

        Call<Pile> call=deckApiService.drawCardsFromDeck(deckID,count);
        Pile drawnCards;

        try {
            drawnCards=call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "drawCardsFromDeck: "+ e.getMessage());
            return null;
        }

        Intent messageIntent=new Intent(MY_SERVICE_MESSAGE);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD,  drawnCards);
        return messageIntent;

    }





    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
