package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class TeamsPojo {

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

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

    @SerializedName("team_id")
    public String team_id;

    @SerializedName("t_name")
    public String t_name;

    @SerializedName("t_logo")
    public String t_logo;


}
