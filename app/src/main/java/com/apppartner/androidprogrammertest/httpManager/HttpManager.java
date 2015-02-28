package com.apppartner.androidprogrammertest.httpManager;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.apppartner.androidprogrammertest.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by ALOKNATH on 2/28/2015.
 */
public class HttpManager {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getData(Map<String, String> credentials) {

        BufferedReader bufferedReader = null;
        HttpURLConnection connection = null;
        OutputStream output = null;
        String username = credentials.get("username");
        String password = credentials.get("password");
        String charset = "UTF-8";
        String line;

        Log.i("UserName And Password Passed: ", username + password);


        try {
            String query = String.format("username=%s&password=%s",
                    URLEncoder.encode(username, charset),
                    URLEncoder.encode(password, charset));

            URL url = new URL(LoginActivity.URI);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

            output = connection.getOutputStream();
            output.write(query.getBytes(charset));


            StringBuilder sb = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }

            Log.i("WEBService Received: ", sb.toString());

            return sb.toString();


        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null || output != null) {
                try {
                    bufferedReader.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

}
