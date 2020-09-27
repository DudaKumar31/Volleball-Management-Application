package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class CurrentSeasonsPojo {

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CurrentSeasonsPojo(String image, String text) {
        this.image = image;
        this.text = text;
    }

    @SerializedName("image")
    private String image;

    @SerializedName("text")
    private String text;



}
