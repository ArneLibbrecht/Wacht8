package com.arnel.wacht8.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnel on 5/04/2018.
 */

public class Deck implements Parcelable{

    @SerializedName("success")
    private Boolean success;

    @SerializedName("deck_id")
    private String deckID;

    @SerializedName("shuffled")
    private Boolean shuffled;

    @SerializedName("remaining")
    private Integer remaining;

    @SerializedName("piles")
    private List<Pile> piles;


    public Deck(Boolean success, String deckID, Integer remaining) {
        this.success = success;
        this.deckID = deckID;
        this.remaining = remaining;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDeckID() {
        return deckID;
    }

    public void setDeckID(String deckID) {
        this.deckID = deckID;
    }

    public Boolean getShuffled() {
        return shuffled;
    }

    public void setShuffled(Boolean shuffled) {
        this.shuffled = shuffled;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public List<Pile> getPiles() {
        return piles;
    }

    public void setPiles(List<Pile> piles) {
        this.piles = piles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.success);
        dest.writeString(this.deckID);
        dest.writeValue(this.shuffled);
        dest.writeValue(this.remaining);
        dest.writeList(this.piles);
    }

    protected Deck(Parcel in) {
        this.success = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deckID = in.readString();
        this.shuffled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.remaining = (Integer) in.readValue(Integer.class.getClassLoader());
        this.piles = new ArrayList<Pile>();
        in.readList(this.piles, Pile.class.getClassLoader());
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel source) {
            return new Deck(source);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };
}
