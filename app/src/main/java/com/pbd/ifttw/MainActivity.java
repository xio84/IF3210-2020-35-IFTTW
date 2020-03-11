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
import com.pbd.ifttw.ui.modules.SensorsModuleFragment;

public class MainActivity extends AppCompatActivity {
    private Bundle data_bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
//        FloatingActionButton fab = findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Main Activity", "request code="+requestCode);
        if (requestCode == NewRoutineFragment.CONDITION_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                data_bundle = data.getExtras();
                if (data_bundle != null) {
                    Log.d("Main Activity", "GET: " +
                            data_bundle.getString(SensorsModuleFragment.CONDITION_TYPE,
                                    "Nothing"));
                } else {
                    Log.d("Main Activity", "Got Null!!");
                }
            }
        }
    }

    public Bundle getData_bundle() {
        return data_bundle;
    }
}