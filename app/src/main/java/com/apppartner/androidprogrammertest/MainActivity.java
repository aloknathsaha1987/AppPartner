package com.apppartner.androidprogrammertest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends ActionBarActivity
{
    private View view;
    private Button button;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        try
        {
            view = getWindow().getDecorView().findViewById(android.R.id.content);
            InputStream ims = getAssets().open("bg_coding_tasks.png");
            Drawable d = Drawable.createFromStream(ims, null);
            view.setBackground(d);

            ims = getAssets().open("btn_main_chat.png");
            d = Drawable.createFromStream(ims, null);
            button = (Button)findViewById(R.id.button_chat);
            button.setBackground(d);

            ims = getAssets().open("btn_main_login.png");
            d = Drawable.createFromStream(ims, null);
            button = (Button)findViewById(R.id.button_login);
            button.setBackground(d);

            ims = getAssets().open("btn_main_animation.png");
            d = Drawable.createFromStream(ims, null);
            button = (Button)findViewById(R.id.button_animation);
            button.setBackground(d);

        }catch(IOException ex)
        {
            Log.e("Error in assignment: ", ex.getMessage());
        }

    }

    public void onLoginButtonClicked(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onChatButtonClicked(View v)
    {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void onAnimationTestButtonClicked(View v)
    {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }
}
