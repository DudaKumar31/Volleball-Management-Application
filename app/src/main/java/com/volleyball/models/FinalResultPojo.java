package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class FinalResultPojo {

    @SerializedName("l")
    private String l;

    @SerializedName("t_logo")
    private String t_logo;

    @SerializedName("w")
    private String w;

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getT_logo() {
        return t_logo;
    }

    public void setT_logo(String t_logo) {
        this.t_logo = t_logo;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    @SerializedName("t_name")
    private String t_name;





}
