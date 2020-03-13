package com.pbd.ifttw.ui.modules;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.NewRoutineFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class TimerModuleFragment extends Fragment {
    private Bundle b = new Bundle();
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_timer, container, false);
        if (getContext() != null) {
            Spinner spinner = root.findViewById(R.id.timer_spinner);
            final Spinner day_spinner = root.findViewById(R.id.day_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.timer_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    DatePicker datePicker = root.findViewById(R.id.datePicker);
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
            ArrayAdapter<CharSequence> day_adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.day_array, android.R.layout.simple_spinner_item);
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
            Button setTimer = root.findViewById(R.id.setButton);
            setTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTimer(v);
                }
            });
        }
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTimer(View view) {
        if (getContext() != null) {

//            Intent timerIntent = new Intent(getContext(), MyReceiver.class);
//            timerIntent.putExtra("condition", "Timer");
//            PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, 1,
//                    timerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
            Spinner spinner = root.findViewById(R.id.timer_spinner);
            Spinner day_spinner = root.findViewById(R.id.day_spinner);
            String position = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
            DatePicker datePicker = root.findViewById(R.id.datePicker);
            TimePicker timePicker = root.findViewById(R.id.timePicker);

            long repeatInterval = AlarmManager.INTERVAL_DAY;
            long triggerTime;
            if (position.equals("Once")) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                triggerTime = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                b.putString(NewRoutineFragment.CONDITION_TYPE, getString(R.string.once_timer_condition_type));
                b.putString(NewRoutineFragment.CONDITION_VALUE, Long.toString(triggerTime));

            } else {
                if (position.equals("Everyday")) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    triggerTime = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    if (calendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                        triggerTime += AlarmManager.INTERVAL_DAY;
                    }
                    b.putString(NewRoutineFragment.CONDITION_TYPE, getString(R.string.repeating_timer_condition_type));
                    b.putString(NewRoutineFragment.CONDITION_VALUE, Long.toString(triggerTime)
                            + "R"
                            + Long.toString(repeatInterval)
                    );
                } else {
                    repeatInterval = 7 * AlarmManager.INTERVAL_DAY;
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
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    triggerTime = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    if (calendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                        triggerTime += 7 + AlarmManager.INTERVAL_DAY;
                    }
                    b.putString(NewRoutineFragment.CONDITION_TYPE, getString(R.string.repeating_timer_condition_type));
                    b.putString(NewRoutineFragment.CONDITION_VALUE, Long.toString(triggerTime)
                            + "R"
                            + Long.toString(repeatInterval)
                    );
                }
            }

            // Return to MainActivity
            Intent replyIntent = new Intent();
            // Put extra data
            replyIntent.putExtras(b);
            // Get parent activity
            Activity parent = getActivity();
            if (parent != null) {
                Log.d("Timer Module", "Sending...");
                // Set parent activity result to RESULT_OK
                parent.setResult(Activity.RESULT_OK, replyIntent);
                // Finish parent activity
                parent.finish();
            } else {
                // Error happened, report!
                Log.d("Timer","Can't find activity!");
            }
//
//            if (alarmManager != null) {
//                alarmManager.cancel(notifyPendingIntent);
//            }
//
//            if (position.equals("Once")) {
//                if (alarmManager != null) {
//                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, notifyPendingIntent);
//                }
//            } else {
//                if (alarmManager != null) {
//                    alarmManager.setInexactRepeating
//                            (AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                                    triggerTime, repeatInterval, notifyPendingIntent);
//                }
//
//            }
        }
    }

}
