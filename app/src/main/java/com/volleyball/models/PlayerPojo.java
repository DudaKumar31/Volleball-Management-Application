package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class PlayerPojo {

    @SerializedName("p_id")
    private String p_id;

    @SerializedName("p_name")
    private String p_name;

    @SerializedName("p_image")
    private String p_image;

    @SerializedName("team_id")
    private String team_id;
    public String getT_name() {
        return t_name;
    }
    public void setT_name(String t_name) {
        this.t_name = t_name;
    }
    public String getT_logo() {
        return t_logo;
    }

    public void setT_logo(String t_logo) {
        this.t_logo = t_logo;
    }

    @SerializedName("t_name")
    private String t_name;

    @SerializedName("t_logo")
    private String t_logo;

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    @SerializedName("point")
    private String point;


    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
}
