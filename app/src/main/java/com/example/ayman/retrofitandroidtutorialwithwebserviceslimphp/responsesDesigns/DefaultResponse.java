package com.example.ayman.retrofitandroidtutorialwithwebserviceslimphp.responsesDesigns;

import com.google.gson.annotations.SerializedName;


//TODO:DESIGN RESPONSE FOR EACH API FOR CODE CONSUMPTION ONLY AND NOT MANDATORY DO THIS..
public class DefaultResponse {

    @SerializedName("error")
    private boolean err;

    @SerializedName("message")
    private String msg;

    public DefaultResponse(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }
}
