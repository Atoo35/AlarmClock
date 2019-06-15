package com.tp.atharva.alarmclock;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;

import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;



import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.Objects;

import static android.os.PowerManager.*;


public class Main2Activity extends AppCompatActivity  {

    private SensorManager sm;
    private ImageView imageView,handimage;
    private Animation mShakeAnimation;

    private float acelVal;
    private float acelLast;
  //  public float shake;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    Vibrator vibrator;
    long[] vibpattern={1000,1000,1000,1000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PARTIAL_WAKE_LOCK,"wake:tagwake");

        wakeLock.acquire(5000);
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_main2);
        vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator.hasVibrator())
            vibrator.vibrate(vibpattern,0);
        imageView =  findViewById(R.id.imgBell);
        handimage = findViewById(R.id.hand);
        mShakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shakeanim);
        imageView.startAnimation(mShakeAnimation);

        TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
                0.0f, 0.0f);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(2);
        handimage.startAnimation(animation);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, Objects.requireNonNull(sm).getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
       // shake = 0.00f;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY,true);

        if(proximitySensor == null){
            Toast.makeText(this, "Proximity sensor not available !", Toast.LENGTH_LONG).show();
            //finish();
        }

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()){
                    Toast.makeText(getApplicationContext(),"Alarm Off!",Toast.LENGTH_SHORT).show();
                    activty2.ringtone.stop();
                    vibrator.cancel();
                    Intent intent = new Intent(getApplicationContext(),activty2.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    finish();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };


    }
    @Override
    protected void onResume(){
        sensorManager.registerListener(proximitySensorListener, proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent .values[0];
            float y = sensorEvent .values[1];
            float z = sensorEvent .values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            //shake = shake * 0.9f + delta;






            if (delta>12) {

                 Toast.makeText(getApplicationContext() , "Alarm Snoozed", Toast.LENGTH_SHORT).show();


                vibrator.cancel();
                activty2.ringtone.stop();
                finish();





            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    };

}
