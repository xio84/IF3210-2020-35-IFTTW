package com.pbd.ifttw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.pbd.ifttw.ui.main.NewRoutineFragment;
import com.pbd.ifttw.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private final String CONDITION_BUNDLE_KEY = "CBK";
    private Bundle condition_bundle = null;
    private final String ACTION_BUNDLE_KEY = "ABK";
    private Bundle action_bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            condition_bundle = savedInstanceState.getBundle(CONDITION_BUNDLE_KEY);
        } else {
            Log.d("Main Activity", "savedInstanceState is null");
        }

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(CONDITION_BUNDLE_KEY, condition_bundle);
        Log.d("Main Activity", "Saving Out_State");
        super.onSaveInstanceState(outState);
    }

    @Nullable
    public Bundle getCondition_bundle() {
        return condition_bundle;
    }

    @Nullable
    public Bundle getAction_bundle() {
        return action_bundle;
    }
}