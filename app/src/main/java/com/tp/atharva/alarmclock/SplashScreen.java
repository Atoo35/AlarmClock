package com.tp.atharva.alarmclock;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen _config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2000)
                .withBackgroundColor(Color.parseColor("#074E72"))
                .withLogo(R.drawable.clock1)
                .withHeaderText("")
                .withFooterText("")
                .withBeforeLogoText("ALARM CLOCK V1.0");

       // _config.getHeaderTextView().setTextColor(Color.WHITE);
      //  _config.getFooterTextView().setTextColor(Color.WHITE);
        _config.getBeforeLogoTextView().setTextColor(Color.WHITE);

        View view = _config.create();
        setContentView(view);
    }
}
