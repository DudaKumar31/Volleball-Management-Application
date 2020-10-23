package com.volleyball.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerScoreModel {
    @SerializedName("p_name")
    @Expose
    private String p_name;

    @SerializedName("score")
    @Expose
    private String score;

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("sid")
    @Expose
    private String sid;


    @SerializedName("team_id")
    @Expose
    private String team_id;


    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }
}
