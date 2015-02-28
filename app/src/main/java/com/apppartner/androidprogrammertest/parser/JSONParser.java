package com.apppartner.androidprogrammertest.parser;

import com.apppartner.androidprogrammertest.data.AuthenticationDetails;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ALOKNATH on 2/28/2015.
 */
public class JSONParser {

    public static AuthenticationDetails parseFeed(String content) {

        AuthenticationDetails details = new AuthenticationDetails();
        try {
            JSONObject jsonObject = new JSONObject(content);
            details.setCode(jsonObject.getString("code"));
            details.setMessage(jsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;
    }
}
