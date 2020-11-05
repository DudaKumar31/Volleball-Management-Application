package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class FeedbackPojo {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @SerializedName("email")
    private String email;

    @SerializedName("msg")
    private String msg;

    @SerializedName("subject")
    private String subject;

}
