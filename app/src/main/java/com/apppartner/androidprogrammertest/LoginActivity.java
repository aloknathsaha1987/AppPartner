package com.apppartner.androidprogrammertest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.apppartner.androidprogrammertest.data.AuthenticationDetails;
import com.apppartner.androidprogrammertest.httpManager.HttpManager;
import com.apppartner.androidprogrammertest.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends ActionBarActivity
{
    private View view;
    private Button button;
    private Toolbar toolbar;
    private String username;
    private String password;
    private EditText text;
    private AuthenticationDetails authenticationDetails;
    private Map<String, String> credentials = new HashMap<>();
    private ProgressDialog progressDialog;
    public static String URI = "http://dev.apppartner.com/AppPartnerProgrammerTest/scripts/login.php";

    private AlertDialog.Builder alertDialog;
    private double startTime;
    private double stopTime;
    private double timeElapsed;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        TextView textView = (TextView)findViewById(R.id.usernameTextView);
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato.ttf");
        textView.setTypeface(typeFace);

        textView = (TextView)findViewById(R.id.passwordTextView);
        typeFace= Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato.ttf");
        textView.setTypeface(typeFace);

        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(LoginActivity.this);

        toolbar = (Toolbar)findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8C000000")));


        try {
            view = getWindow().getDecorView().findViewById(android.R.id.content);
            InputStream ims = getAssets().open("bg_login.png");
            Drawable d = Drawable.createFromStream(ims, null);
            view.setBackground(d);

            ims = getAssets().open("btn_login.png");
            d = Drawable.createFromStream(ims, null);
            button = (Button) findViewById(R.id.button_login);
            button.setBackground(d);

            ims = getAssets().open("btn_back.png");
            d = Drawable.createFromStream(ims, null);
            if(toolbar != null){
                toolbar.setLogo(d);
                toolbar.setLogoDescription("Login");
            }


        }catch(IOException ex)
        {
            Log.e("Error in assignment: ", ex.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    public void sendAsyncPost(View v){

        text = (EditText)findViewById(R.id.usernameTextView);
        username = text.getText().toString();
        text = (EditText)findViewById(R.id.passwordTextView);
        password = text.getText().toString();

        if(username.length() == 0){
            Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show();
        }else if (password.length() == 0){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }else{

            // Call the Async Task to verify the authorization credentials
            credentials.put("username", username);
            credentials.put("password", password);
            AuthorizeCredentialsAsyncTask asyncTask = new AuthorizeCredentialsAsyncTask();
            asyncTask.execute(credentials);
        }
    }

    private void generateDialog() {
        if(authenticationDetails != null){
            alertDialog.setTitle(authenticationDetails.getCode());
            alertDialog.setMessage(authenticationDetails.getMessage() + "\n" + "Time Taken: " + String.valueOf(authenticationDetails.getTime()) + " milliseconds");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(authenticationDetails.getCode().equals("Success")) {
                        finish();
                    }
                    authenticationDetails = null;
                }
            });
            alertDialog.show();
        }
    }


    private class AuthorizeCredentialsAsyncTask extends AsyncTask<Map<String, String>, Void , AuthenticationDetails>{

        public AuthorizeCredentialsAsyncTask (){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            startTime = System.currentTimeMillis() % 1000;
            progressDialog.setMessage("Authenticating User Credentials");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected AuthenticationDetails doInBackground(Map<String, String>... maps) {

            String content = HttpManager.getData(maps[0]);
            AuthenticationDetails details = JSONParser.parseFeed(content);

            return details;
        }

        @Override
        protected void onPostExecute(AuthenticationDetails details) {

            progressDialog.hide();
            stopTime = System.currentTimeMillis() % 1000;
            timeElapsed = stopTime - startTime;
            authenticationDetails = details;
            authenticationDetails.setTime(timeElapsed);
            generateDialog();
        }
    }
}
