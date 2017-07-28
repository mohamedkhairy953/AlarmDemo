package com.example.moham.alarmdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AlarmManager alarm_manager;
    TimePicker alarm_timePicker;
    Button btn_turn_on, btn_turn_off;
    Context context;
    TextView set_alarm;
    Calendar calendar;
    PendingIntent pending_intent;
    Spinner spinner;
    ArrayAdapter adapter;
     Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timePicker = (TimePicker) findViewById(R.id.timePicker);
        btn_turn_off = (Button) findViewById(R.id.turnoff_btn);
        btn_turn_on = (Button) findViewById(R.id.turnon_btn);
        set_alarm = (TextView) findViewById(R.id.set_alarm_txt);
        spinner = (Spinner) findViewById(R.id.spinner);
        calendar = Calendar.getInstance();//
         intent = new Intent(this.context, AlarmReceiver.class);
        adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.songs));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        btn_turn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarm_timePicker.getCurrentMinute());
                int hour = alarm_timePicker.getCurrentHour();
                int minut = alarm_timePicker.getCurrentMinute();

                set_alarm.setText("set time to " + hour + ((minut >= 10) ? ":" : ":0") + minut);
                intent.putExtra("Extra", "Alarm_on");
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() , pending_intent);

            }
        });
        btn_turn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarm_manager.cancel(pending_intent);
                set_alarm.setText("Turn off");
                intent.putExtra("Extra", "Alarm_off");
                sendBroadcast(intent);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getSelectedItemPosition() >0){
            intent.putExtra("song",parent.getSelectedItem().toString());
            Toast.makeText(MainActivity.this,parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
            intent.putExtra("song","no");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
