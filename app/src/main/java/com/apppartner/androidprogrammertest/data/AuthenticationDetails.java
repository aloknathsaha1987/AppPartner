package com.apppartner.androidprogrammertest.data;

/**
 * Created by ALOKNATH on 2/28/2015.
 */
public class AuthenticationDetails{
    private String code;
    private String message;
    private double time;

    public AuthenticationDetails(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
