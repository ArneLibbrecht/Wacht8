package com.arnel.wacht8.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by arnel on 5/04/2018.
 */

public class Pile implements Parcelable{

    @SerializedName("remaining")
    private Integer remaining;

    @SerializedName("cards")
    private List<Card> cards;

    public Pile(Integer remaining, Card[] cards) {
        this.remaining = remaining;
        this.cards = new ArrayList<>(Arrays.asList(cards));
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = new ArrayList<>(Arrays.asList(cards));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.remaining);
        dest.writeTypedList(this.cards);
    }

    protected Pile(Parcel in) {
        this.remaining = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cards = in.createTypedArrayList(Card.CREATOR);
    }

    public static final Creator<Pile> CREATOR = new Creator<Pile>() {
        @Override
        public Pile createFromParcel(Parcel source) {
            return new Pile(source);
        }

        @Override
        public Pile[] newArray(int size) {
            return new Pile[size];
        }
    };
}
