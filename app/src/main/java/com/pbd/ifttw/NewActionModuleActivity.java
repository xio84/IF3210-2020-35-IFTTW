package com.pbd.ifttw;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.pbd.ifttw.ui.main.ActionModulesPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class NewActionModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action_module);
        ActionModulesPagerAdapter actionModulesPagerAdapter = new ActionModulesPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(actionModulesPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}