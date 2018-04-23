package com.arnel.wacht8.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by arnel on 5/04/2018.
 */

public class Card implements Parcelable{


    @SerializedName("image")
    private String imagePath;
    @SerializedName("value")
    private String value;
    @SerializedName("code")
    private String code;
    @SerializedName("suit")
    private String suit;
    @SerializedName("images")
    private HashMap<String,String> imagePathList=new HashMap<>();

    public Card(String imagePath, String value, String code, String suit, HashMap<String, String> imagePathList) {
        this.imagePath = imagePath;
        this.value = value;
        this.code = code;
        this.suit = suit;
        this.imagePathList = imagePathList;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public HashMap<String, String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(HashMap<String, String> imagePathList) {
        this.imagePathList = imagePathList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
        dest.writeString(this.value);
        dest.writeString(this.code);
        dest.writeString(this.suit);
        dest.writeSerializable(this.imagePathList);
    }

    protected Card(Parcel in) {
        this.imagePath = in.readString();
        this.value = in.readString();
        this.code = in.readString();
        this.suit = in.readString();
        this.imagePathList = (HashMap<String, String>) in.readSerializable();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
