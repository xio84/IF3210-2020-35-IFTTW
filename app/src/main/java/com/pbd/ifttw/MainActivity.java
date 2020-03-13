package com.pbd.ifttw;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.pbd.ifttw.database.Routine;
import com.pbd.ifttw.database.SQLiteRoutineDatabaseHelper;
import com.pbd.ifttw.ui.main.NewRoutineFragment;
import com.pbd.ifttw.ui.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String CONDITION_BUNDLE_KEY = "CBK";
    public static NotificationManagerCompat notificationManager;
    public static RequestQueue queue;
    public void setCondition_bundle(Bundle condition_bundle) {
        this.condition_bundle = condition_bundle;
    }

    public void setAction_bundle(Bundle action_bundle) {
        this.action_bundle = action_bundle;
    }

    private Bundle condition_bundle = null;
    public static final String ACTION_BUNDLE_KEY = "ABK";
    private Bundle action_bundle = null;

    public SQLiteRoutineDatabaseHelper getDb() {
        return db;
    }

    private SQLiteRoutineDatabaseHelper db;
    private List<Routine> listRoutine = new ArrayList<Routine>();

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notify_me", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            name = "API";
            description = "Channel for API";
            channel = new NotificationChannel("api", name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();

        // Get database
        db = new SQLiteRoutineDatabaseHelper(this);

        // Convert to List
        listRoutine.addAll(db.allRoutine());

        // Set THIS & WHAT button text
        if (savedInstanceState != null) {
            Log.d("Main Activity", "savedInstanceState is not null");
            condition_bundle = savedInstanceState.getBundle(CONDITION_BUNDLE_KEY);
            action_bundle = savedInstanceState.getBundle(ACTION_BUNDLE_KEY);
        } else {
            Log.d("Main Activity", "savedInstanceState is null");
        }

        // Tab layout
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Main Activity", "request code="+requestCode);
        if (requestCode == NewRoutineFragment.CONDITION_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                condition_bundle = data.getExtras();
                if (condition_bundle != null) {
                    Log.d("Main Activity", "GET: " +
                            condition_bundle.getString(NewRoutineFragment.CONDITION_TYPE,
                                    "Nothing"));
                } else {
                    Log.d("Main Activity", "Got Null!!");
                }
            }
        }
        if (requestCode == NewRoutineFragment.ACTION_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                action_bundle = data.getExtras();
                if (action_bundle != null) {
                    Log.d("Main Activity", "GET: " +
                            action_bundle.getString(NewRoutineFragment.ACTION_TYPE,
                                    "Nothing"));
                } else {
                    Log.d("Main Activity", "Got Null!!");
                }
            }
        }
    }

    // Keep text on device rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(CONDITION_BUNDLE_KEY, condition_bundle);
        outState.putBundle(ACTION_BUNDLE_KEY, action_bundle);
        Log.d("Main Activity", "Saving Out_State");
        super.onSaveInstanceState(outState);
    }

    // Called by NewRoutineFragment to get bundles
    @Nullable
    public Bundle getCondition_bundle() {
        return condition_bundle;
    }
    @Nullable
    public Bundle getAction_bundle() {
        return action_bundle;
    }

    // Called by RoutineListFragment
    public List<Routine> getListRoutine() {
        return listRoutine;
    }
}