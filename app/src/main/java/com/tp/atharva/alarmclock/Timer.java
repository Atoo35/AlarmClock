package com.tp.atharva.alarmclock;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

public class Timer extends AppCompatActivity {
    Chronometer chronometer;
    EditText h,m,s;
    Button b1,b2;
   // boolean flag=false;
    long sum,ss1,hh1,mm1;
    int a=0;
    Ringtone ringtone;
    Vibrator vibrator;
    long[] vibpattern={0,200,200,200};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);

        setContentView(R.layout.activity_timer);
        h=findViewById(R.id.hh);
        m=findViewById(R.id.mm);
        s=findViewById(R.id.ss);
        b1=findViewById(R.id.b2);
        b2=findViewById(R.id.stop);

        b2.setEnabled(false);
        chronometer=findViewById(R.id.c1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=0;
                calcsum();
                chronometer.setBase(SystemClock.elapsedRealtime()+sum);
                chronometer.start();
                s.setEnabled(false);
                m.setEnabled(false);
                h.setEnabled(false);
                b1.setEnabled(false);
                b2.setEnabled(true);


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                s.setEnabled(true);
                m.setEnabled(true);
                h.setEnabled(true);
                b1.setEnabled(true);
                b2.setEnabled(false);
                if(ringtone.isPlaying())
                    ringtone.stop();
                vibrator.cancel();

            }
        });


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().toString().equalsIgnoreCase("00:00")){
                    if(a>0){
                        ringtone.play();
                        if (vibrator.hasVibrator()){
                            vibrator.vibrate(vibpattern,0);
                        }
                    }

                    a++;
                    chronometer.stop();
                }
            }
        });

    }
    public void calcsum(){
        ss1 = Long.parseLong(s.getText().toString());
        ss1*=1000;
        hh1 = Long.parseLong(h.getText().toString());
        hh1=hh1*(60*60*1000);
        mm1 = Long.parseLong(m.getText().toString());
        mm1=mm1*(60*1000);
        sum=ss1+mm1+hh1;
    }
}
