package com.arnel.wacht8.services;

import com.arnel.wacht8.model.Card;
import com.arnel.wacht8.model.Deck;
import com.arnel.wacht8.model.Pile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by arnel on 5/04/2018.
 */

public interface DeckApiService {
   String BASE_URL="https://deckofcardsapi.com/api/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
            .build();


    @GET("deck/new/shuffle")
    Call<Deck> getNewDeck();

    @GET("deck/{deckID}/draw/?count={countNumber}")
    Call<Pile> drawCardsFromDeck(@Path("deckID") String deckID, @Path("countNumber") Integer countNumber);



}
