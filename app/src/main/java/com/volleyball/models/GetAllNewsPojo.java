package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class GetAllNewsPojo {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("image")
    private String image;

    @SerializedName("msg")
    private String msg;

    @SerializedName("title")
    private String title;

}
