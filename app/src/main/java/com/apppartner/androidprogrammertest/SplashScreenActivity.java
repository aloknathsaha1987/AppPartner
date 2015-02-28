package com.apppartner.androidprogrammertest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ALOKNATH on 2/27/2015.
 */
public class SplashScreenActivity  extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        ImageView view = (ImageView)findViewById(R.id.imageView);
        try
        {
            InputStream ims = getAssets().open("bg_splash.png");
            Drawable d = Drawable.createFromStream(ims, null);
            view.setImageDrawable(d);
            view.setScaleType(ImageView.ScaleType.FIT_XY);

        }catch(IOException ex)
        {
            return;
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
