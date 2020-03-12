package com.pbd.ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Spinner spinner = findViewById(R.id.timer_spinner);
        final Spinner day_spinner = findViewById(R.id.day_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.timer_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                DatePicker datePicker = findViewById(R.id.datePicker);
                String position = (String) parent.getItemAtPosition(pos);
                if (position.equals("Once")) {
                    datePicker.setVisibility(View.VISIBLE);
                    day_spinner.setVisibility(View.INVISIBLE);
                } else {
                    if (position.equals("Weekly")) {
                        datePicker.setVisibility(View.INVISIBLE);
                        day_spinner.setVisibility(View.VISIBLE);
                    } else {
                        datePicker.setVisibility(View.INVISIBLE);
                        day_spinner.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        ArrayAdapter<CharSequence> day_adapter = ArrayAdapter.createFromResource(this, R.array.day_array, android.R.layout.simple_spinner_item);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_spinner.setAdapter(day_adapter);
        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTimer(View view) {
        Intent timerIntent = new Intent(this, MyReceiver.class);
        timerIntent.putExtra("condition", "Timer");
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, 1, timerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Spinner spinner = findViewById(R.id.timer_spinner);
        Spinner day_spinner = findViewById(R.id.day_spinner);
        String position = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());

        long repeatInterval = AlarmManager.INTERVAL_DAY;

        if (position.equals("Weekly")) {
            repeatInterval = 7 * AlarmManager.INTERVAL_DAY;
        }

        DatePicker datePicker = findViewById(R.id.datePicker);
        TimePicker timePicker = findViewById(R.id.timePicker);
        long triggerTime;
        if (position.equals("Once")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
            triggerTime = calendar.getTimeInMillis() - SystemClock.elapsedRealtime();
        } else {
            if (position.equals("Everyday")) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                triggerTime = calendar.getTimeInMillis() - SystemClock.elapsedRealtime();
                if (calendar.getTimeInMillis() < SystemClock.elapsedRealtime()) {
                    triggerTime += AlarmManager.INTERVAL_DAY;
                }
            } else {
                int targetDay = 0;
                Calendar calendar = Calendar.getInstance();
                String pickedDay = (String) day_spinner.getItemAtPosition(spinner.getSelectedItemPosition());
                if (pickedDay.equals("Monday")) {
                    targetDay = 1;
                }
                if (pickedDay.equals("Tuesday")) {
                    targetDay = 2;
                }
                if (pickedDay.equals("Wednesday")) {
                    targetDay = 3;
                }
                if (pickedDay.equals("Thursday")) {
                    targetDay = 4;
                }
                if (pickedDay.equals("Friday")) {
                    targetDay = 5;
                }
                if (pickedDay.equals("Saturday")) {
                    targetDay = 6;
                }
                if (pickedDay.equals("Sunday")) {
                    targetDay = 7;
                }
                targetDay -= calendar.get(Calendar.DAY_OF_WEEK);
                calendar.add(Calendar.DAY_OF_WEEK, targetDay);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                triggerTime = calendar.getTimeInMillis() - SystemClock.elapsedRealtime();
                if (calendar.getTimeInMillis() < SystemClock.elapsedRealtime()) {
                    triggerTime += 7 + AlarmManager.INTERVAL_DAY;
                }
            }
        }
        if (alarmManager != null) {
            alarmManager.cancel(notifyPendingIntent);
        }

        if (position.equals("Once")) {
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, notifyPendingIntent);
            }
        } else {
            if (alarmManager != null) {
                alarmManager.setInexactRepeating
                        (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                triggerTime, repeatInterval, notifyPendingIntent);
            }

        }

    }

}
