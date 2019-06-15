package com.tp.atharva.alarmclock;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MotionEvent;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;
import static android.view.GestureDetector.*;

import android.support.design.widget.Snackbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements OnGestureListener {
    TimePicker timePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    ToggleButton toggleButton;
    long time;
    Spinner spinner;

    private GestureDetectorCompat gestureDetectorCompat;
    String[] minutes={"1","5","10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        timePicker = findViewById(R.id.timepicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        toggleButton=findViewById(R.id.toggleButton);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,minutes);
        spinner.setAdapter(adapter);
        gestureDetectorCompat = new GestureDetectorCompat(this,this);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        switch (item.getItemId()){

            case R.id.stopwatch:
            {
                //Toast.makeText(this,"Stopwatch",Toast.LENGTH_SHORT).show();
                Intent i4 = new Intent(this,Main3Activity.class);
                startActivity(i4);
                return true;
            }
            case R.id.timer:
            {
                //Toast.makeText(this,"Timer",Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(this,Timer.class);
                startActivity(i3);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void OnToggleClicked(View view)
    {

        if (toggleButton.isChecked())
        {

            Snackbar.make(view,"ALARM SET",Snackbar.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            Intent intent = new Intent(this, activty2.class);

            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
            if(System.currentTimeMillis()>time)
            {
                if (calendar.AM_PM == 0)
                    time = time + (1000*60*60*12);
                else
                    time = time + (1000*60*60*24);
            }


           alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 60*1000*(Long.parseLong(spinner.getSelectedItem().toString())), pendingIntent);




        }
        else
        {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onDown(MotionEvent e) {


        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //Toast.makeText(getApplicationContext(),"Redirecting you to Tic Tac Toe",Toast.LENGTH_SHORT).show();
        Intent ii = new Intent(getApplicationContext(),Tcitactoe.class);
        startActivity(ii);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
