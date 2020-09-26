package com.volleyball.models;

import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public String status;
}
