package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class GetAllSchedulePojo {

    @SerializedName("result")
    private String result;

    @SerializedName("season")
    private String season;

    @SerializedName("s_date")
    private String s_date;

    @SerializedName("s_id")
    private String s_id;

    @SerializedName("team1_id")
    private String team1_id;

    @SerializedName("team1_logo")
    private String team1_logo;

    @SerializedName("team1_name")
    private String team1_name;

    @SerializedName("team1_score")
    private String team1_score;

    @SerializedName("team2_id")
    private String team2_id;

    @SerializedName("team2_logo")
    private String team2_logo;

    @SerializedName("team2_name")
    private String team2_name;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getS_date() {
        return s_date;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getTeam1_id() {
        return team1_id;
    }

    public void setTeam1_id(String team1_id) {
        this.team1_id = team1_id;
    }

    public String getTeam1_logo() {
        return team1_logo;
    }

    public void setTeam1_logo(String team1_logo) {
        this.team1_logo = team1_logo;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam1_score() {
        return team1_score;
    }

    public void setTeam1_score(String team1_score) {
        this.team1_score = team1_score;
    }

    public String getTeam2_id() {
        return team2_id;
    }

    public void setTeam2_id(String team2_id) {
        this.team2_id = team2_id;
    }

    public String getTeam2_logo() {
        return team2_logo;
    }

    public void setTeam2_logo(String team2_logo) {
        this.team2_logo = team2_logo;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getTeam2_score() {
        return team2_score;
    }

    public void setTeam2_score(String team2_score) {
        this.team2_score = team2_score;
    }

    @SerializedName("team2_score")
    private String team2_score;



}
