package com.tp.atharva.alarmclock;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

import java.io.Serializable;


public class activty2 extends BroadcastReceiver  {
static Ringtone ringtone;


    @Override
    public void onReceive(Context context, Intent intent){



        //Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);

        ringtone.play();




        Intent intent1 = new Intent();
        intent1.setClassName("com.tp.atharva.alarmclock","com.tp.atharva.alarmclock.Main2Activity");
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent1);

    }



}

