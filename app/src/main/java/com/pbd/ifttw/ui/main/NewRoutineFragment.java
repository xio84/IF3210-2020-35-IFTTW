package com.pbd.ifttw.ui.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.icu.util.Calendar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pbd.ifttw.MainActivity;
import com.pbd.ifttw.NewActionModuleActivity;
import com.pbd.ifttw.NewConditionModuleActivity;
import com.pbd.ifttw.R;
import com.pbd.ifttw.database.Routine;
import com.pbd.ifttw.database.SQLiteRoutineDatabaseHelper;
import com.pbd.ifttw.service.SensorBackgroundService;
import com.pbd.ifttw.service.TimerBackgroundReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class NewRoutineFragment extends Fragment {
    private String LOG_TAG = "New Routine";
    public static final int CONDITION_REQUEST = 1;
    public static final int ACTION_REQUEST = 2;
    public static final String CONDITION_TYPE = "condition_type";
    public static final String CONDITION_VALUE = "condition_value";
    public static final String ACTION_TYPE = "action_type";
    public static final String ACTION_VALUE = "action_value";
    private String condition_type = "NONE";
    private String action_type = "NONE";
    private String condition_value = "NONE";
    private String action_value = "NONE";
    private String routine_name = null;

    private View root;
    private MainActivity parent;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_new_routine, container, false);
        Button thisButton = root.findViewById(R.id.thisButton);
        thisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNewConditionModuleActivity(v);
            }
        });
        Button whatButton = root.findViewById(R.id.whatButton);
        whatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNewActionModuleActivity(v);
            }
        });
        FloatingActionButton addRoutine = root.findViewById(R.id.addRoutine);
        addRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoutine(v);
            }
        });
        parent = (MainActivity) getActivity();
        setButtonText();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        parent = (MainActivity) getActivity();
        setButtonText();
    }

    private void launchNewConditionModuleActivity(@Nullable View view) {
        Log.d(LOG_TAG, "This button Clicked!");
        Intent intent = new Intent(getActivity(), NewConditionModuleActivity.class);
        if (parent != null) {
            parent.startActivityForResult(intent, CONDITION_REQUEST);
        } else {
            Log.d("New Routine Fragment", "Can't getActivity()");
        }
    }

    private void launchNewActionModuleActivity(@Nullable View view) {
        Log.d(LOG_TAG, "What button Clicked!");
        Intent intent = new Intent(getActivity(), NewActionModuleActivity.class);
        if (parent != null) {
            parent.startActivityForResult(intent, ACTION_REQUEST);
        } else {
            Log.d("New Routine Fragment", "Can't getActivity()");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setButtonText() {
        // Set text of main activity
        if (parent != null) {
            // Handle THIS button text
            Bundle condition_bundle = parent.getCondition_bundle();
            Button thisButton = root.findViewById(R.id.thisButton);
            if (condition_bundle != null) {
                condition_type = condition_bundle.getString(CONDITION_TYPE, "NONE");
                if (!condition_type.equals("NONE")) {
                    // Handle Proximity module
                    if (condition_type.equals("proximity")) {
                        String valString;
                        condition_value = condition_bundle.getString(CONDITION_VALUE, "NONE");
                        switch (condition_value) {
                            case "0":
                                valString = "Proximity is Near";
                                break;
                            case "1":
                                valString = "Proximity is Far";
                                break;
                            default:
                                valString = "SOMETHING IS WRONG";
                        }
                        thisButton.setText(valString);
                    }
                    // Handle once timer module
                    if (condition_type.equals(getString(R.string.once_timer_condition_type))) {
                        Log.d("the time is:", Long.toString(Calendar.getInstance().getTimeInMillis()));
                        Log.d("the time is:", condition_value);
                        String valString;
                        condition_value = condition_bundle.getString(CONDITION_VALUE, "NONE");
                        condition_value = Long.toString(Long.parseLong(condition_value) + Calendar.getInstance().getTimeInMillis());
                        long millis = Long.parseLong(condition_value);
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(millis);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.US);
//                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
                        String dateString = dateFormat.format(cal.getTime());
                        thisButton.setText("Time is " + dateString);
                    }
                    // Handle repeating timer module
                    if (condition_type.equals(getString(R.string.repeating_timer_condition_type))) {
                        String valString;
                        condition_value = condition_bundle.getString(CONDITION_VALUE, "NONE");
                        String[] values = condition_value.split("R", 2);
                        condition_value = Long.toString(Long.parseLong(values[0]) + Calendar.getInstance().getTimeInMillis()) + "R" + values[1];
                        values = condition_value.split("R", 2);
                        long millisStart = Long.parseLong(values[0]);
                        long millisInterval = Long.parseLong(values[1]);

                        Calendar calStart = Calendar.getInstance();
                        calStart.setTimeInMillis(millisStart);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.US);
//                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
                        String dateString = dateFormat.format(calStart.getTime());

                        if (millisInterval == AlarmManager.INTERVAL_DAY) {

                            thisButton.setText("Everyday from " + dateString);
                        } else {
                            thisButton.setText("Every week from " + dateString);
                        }
                    }
                }
            } else {
                thisButton.setText(R.string.new_routine_button1_text);
            }
            // Handle WHAT Button
            Bundle action_bundle = parent.getAction_bundle();
            Button whatButton = root.findViewById(R.id.whatButton);
            if (action_bundle != null) {
                action_type = action_bundle.getString(ACTION_TYPE, "NONE");
                if (!action_type.equals("NONE")) {
                    //Handle WiFi module
                    if (action_type.equals("wifi")) {
                        String valString;
                        action_value = action_bundle.getString(ACTION_VALUE, "NONE");
                        switch (action_value) {
                            case "0":
                                valString = "Turn Wifi Off";
                                break;
                            case "1":
                                valString = "Turn Wifi On";
                                break;
                            default:
                                valString = "SOMETHING IS WRONG";
                        }
                        whatButton.setText(valString);
                    }
                    if (action_type.equals("notify")) {
                        String valString;
                        action_value = action_bundle.getString(ACTION_VALUE, "NONE");
                        whatButton.setText(action_value);
                    }

                    if (action_type.equals("api")) {
                        String valString;
                        action_value = action_bundle.getString(ACTION_VALUE, "NONE");
                        whatButton.setText("API");
                    }
                }
            } else {
                whatButton.setText(R.string.new_routine_button2_text);
            }
        } else {
            Log.d("New Routine Fragment", "Parent is Null!");
        }
    }

    private void addRoutine(View v) {
        EditText name_input = root.findViewById(R.id.editText);
        String name = name_input.getText().toString();
        if (!condition_type.equals("NONE")
                && !action_type.equals("NONE")
                && !name.equals("")
        ) {
            SQLiteRoutineDatabaseHelper db = parent.getDb();
            List<Routine> routineList = parent.getListRoutine();
            int newIndex;
            if (routineList.size() == 0) {
                newIndex = 0;
            } else {
                newIndex = Integer.parseInt(routineList.get(routineList.size() - 1).getId()) + 1;
            }
            if (condition_type.equals("proximity")) {
                addSensorAlarmManager(v, newIndex);
                db.addRoutine(new Routine(Integer.toString(newIndex), name, condition_type, condition_value, action_type, action_value, "active"));
                // Routine created notification
                Toast.makeText(getContext(), name + " Created!", Toast.LENGTH_SHORT).show();
                // Refresh activity
                Intent intent = parent.getIntent();
                parent.finish();
                startActivity(intent);
            }
            if (condition_type.equals(getString(R.string.once_timer_condition_type))) {
                addOnceTimerAlarmManager(v, newIndex);
                db.addRoutine(new Routine(Integer.toString(newIndex), name, condition_type, condition_value, action_type, action_value, "active"));
                // Routine created notification
                Toast.makeText(getContext(), name + " Created!", Toast.LENGTH_SHORT).show();
                // Refresh activity
                Intent intent = parent.getIntent();
                parent.finish();
                startActivity(intent);
            }
            if (condition_type.equals(getString(R.string.repeating_timer_condition_type))) {
                addRepeatingTimerAlarmManager(v, newIndex);
                db.addRoutine(new Routine(Integer.toString(newIndex), name, condition_type, condition_value, action_type, action_value, "active"));
                // Routine created notification
                Toast.makeText(getContext(), name + " Created!", Toast.LENGTH_SHORT).show();
                // Refresh activity
                Intent intent = parent.getIntent();
                parent.finish();
                startActivity(intent);
            }
        }
    }

    private void addSensorAlarmManager(View v, int index) {
        if (getActivity() != null) {
            // get scheduler and prepare intent
            AlarmManager scheduler = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            // add some extras for config
            Bundle args = new Bundle();
            args.putString(CONDITION_TYPE, condition_type);
            args.putString(ACTION_TYPE, action_type);
            args.putString(CONDITION_VALUE, condition_value);
            args.putString(ACTION_VALUE, action_value);
            if (condition_value.equals("0")) {
                args.putFloat(SensorBackgroundService.KEY_THRESHOLD_MIN_VALUE, 1);
            } else {
                args.putFloat(SensorBackgroundService.KEY_THRESHOLD_MAX_VALUE, 7);
            }
            Log.d("action_type", action_type);
            if (action_type.equals("wifi")) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SensorBackgroundService.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;
                interval = 1000L;

                PendingIntent scheduledIntent = PendingIntent.getService(getActivity().getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                scheduler.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, scheduledIntent);
            }
            if (action_type.equals("notify")) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SensorBackgroundService.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;
                interval = 1000L;

                PendingIntent scheduledIntent = PendingIntent.getService(getActivity().getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                scheduler.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, scheduledIntent);
            }
            if (action_type.equals("api")) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SensorBackgroundService.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;
                interval = 1000L;

                PendingIntent scheduledIntent = PendingIntent.getService(getActivity().getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                scheduler.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, scheduledIntent);
            }
        }
    }

    private void addOnceTimerAlarmManager(View v, int index) {
        if (getActivity() != null) {
            // get scheduler and prepare intent
            AlarmManager scheduler = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            // add some extras for config
            Bundle args = new Bundle();
            args.putString(CONDITION_TYPE, condition_type);
            args.putString(ACTION_TYPE, action_type);
            args.putString(CONDITION_VALUE, condition_value);
            args.putString(ACTION_VALUE, action_value);

            if (action_type.equals("wifi") && action_value != null) {
//                Log.d("addOnceTimer", "Created");
                Intent intent = new Intent(getActivity().getApplicationContext(), TimerBackgroundReceiver.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;
                interval = 1000L;

                PendingIntent scheduledIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                long triggerMilis = Long.parseLong(condition_value);
                scheduler.set(AlarmManager.RTC, triggerMilis, scheduledIntent);
            }
            if (action_type.equals("notify")) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TimerBackgroundReceiver.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;
                interval = 1000L;

                PendingIntent scheduledIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                long triggerMilis = Long.parseLong(condition_value);
                scheduler.set(AlarmManager.RTC, triggerMilis, scheduledIntent);
            }
            if (action_type.equals("api")) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TimerBackgroundReceiver.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;
                interval = 1000L;

                PendingIntent scheduledIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                long triggerMilis = Long.parseLong(condition_value);
                scheduler.set(AlarmManager.RTC, triggerMilis, scheduledIntent);
            }
        }
    }

    private void addRepeatingTimerAlarmManager(View v, int index) {
        if (getActivity() != null) {
            // get scheduler and prepare intent
            AlarmManager scheduler = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            // add some extras for config
            Bundle args = new Bundle();
            args.putString(CONDITION_TYPE, condition_type);
            args.putString(ACTION_TYPE, action_type);
            args.putString(CONDITION_VALUE, condition_value);
            args.putString(ACTION_VALUE, action_value);

            if (action_type.equals("wifi") && action_value != null) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TimerBackgroundReceiver.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;

                String[] values = condition_value.split("R", 2);
                interval = Long.parseLong(values[1]);

                PendingIntent scheduledIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                long triggerMilis = Long.parseLong(values[0]);
                Log.d("Alarm set at", String.valueOf(triggerMilis));
                scheduler.setInexactRepeating(AlarmManager.RTC, triggerMilis, interval, scheduledIntent);
            }
            if (action_type.equals("notify") && action_value != null) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TimerBackgroundReceiver.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;

                String[] values = condition_value.split("R", 2);
                interval = Long.parseLong(values[1]);

                PendingIntent scheduledIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                long triggerMilis = Long.parseLong(values[0]);
                Log.d("Alarm set at", String.valueOf(triggerMilis));
                scheduler.setInexactRepeating(AlarmManager.RTC, triggerMilis, interval, scheduledIntent);
            }
            if (action_type.equals("api") && action_value != null) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TimerBackgroundReceiver.class);
                intent.putExtras(args);
                // try getting interval option
                long interval;

                String[] values = condition_value.split("R", 2);
                interval = Long.parseLong(values[1]);

                PendingIntent scheduledIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // start the service
                long triggerMilis = Long.parseLong(values[0]);
                Log.d("Alarm set at", String.valueOf(triggerMilis));
                scheduler.setInexactRepeating(AlarmManager.RTC, triggerMilis, interval, scheduledIntent);
            }
        }
    }
}
