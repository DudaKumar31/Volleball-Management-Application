package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class GetAllSeasonsPojo {


    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_logo() {
        return s_logo;
    }

    public void setS_logo(String s_logo) {
        this.s_logo = s_logo;
    }

    @SerializedName("s_name")
    private String s_name;

    @SerializedName("s_logo")
    private String s_logo;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @SerializedName("Id")
    private String Id;


}
