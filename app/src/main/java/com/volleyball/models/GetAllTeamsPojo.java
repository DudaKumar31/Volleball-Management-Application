package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class GetAllTeamsPojo {
    public String getT_logo() {
        return t_logo;
    }

    public void setT_logo(String t_logo) {
        this.t_logo = t_logo;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    @SerializedName("team_id")
    private String team_id;

    @SerializedName("t_logo")
    private String t_logo;

    @SerializedName("t_name")
    private String t_name;
}
