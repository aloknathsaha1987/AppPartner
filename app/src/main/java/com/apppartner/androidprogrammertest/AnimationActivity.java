package com.apppartner.androidprogrammertest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;


public class AnimationActivity extends ActionBarActivity implements View.OnTouchListener
{
    private Toolbar toolbar;
    private View view;
    private ImageView imageView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_animation);

        toolbar = (Toolbar)findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Animation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C8000000")));


        try {
            view = getWindow().getDecorView().findViewById(android.R.id.content);
            InputStream ims = getAssets().open("bg_animation.png");
            Drawable d = Drawable.createFromStream(ims, null);
            view.setBackground(d);

            Button animation = (Button)findViewById(R.id.button_animation);
            ims = getAssets().open("btn_fade.png");
            d = Drawable.createFromStream(ims, null);
            animation.setBackground(d);

            ImageView imageViewContainer = (ImageView)findViewById(R.id.imageView_container);
            ims = getAssets().open("bg_cell_animation_test.png");
            d = Drawable.createFromStream(ims, null);
            imageViewContainer.setBackground(d);

            imageView = (ImageView)findViewById(R.id.imageView);
            ims = getAssets().open("ic_apppartner.png");
            d = Drawable.createFromStream(ims, null);
            imageView.setBackground(d);
            imageView.setOnTouchListener(this);
            imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely));

        }catch(IOException ex){
            Log.e("Error in assignment: ", ex.getMessage());
        }
    }

    public void animate(View v){

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imageView, "alpha",
                0f);
        fadeOut.setDuration(2000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imageView, "alpha",
                0f, 1f);
        fadeIn.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeIn).after(fadeOut);
        animatorSet.start();

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

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();

        if (view.getId() != R.id.imageView) return false;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                params.topMargin = (int) event.getRawY() - view.getHeight();
                params.leftMargin = (int) event.getRawX() - (view.getWidth() / 2);
                view.setLayoutParams(params);
                break;

            case MotionEvent.ACTION_UP:
                params.topMargin = (int) event.getRawY() - view.getHeight();
                params.leftMargin = (int) event.getRawX() - (view.getWidth() / 2);
                view.setLayoutParams(params);
                break;

            case MotionEvent.ACTION_DOWN:
                view.setLayoutParams(params);
                break;
        }

        return true;

    }
}
