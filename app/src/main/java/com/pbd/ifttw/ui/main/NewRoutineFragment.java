package com.pbd.ifttw.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pbd.ifttw.NewActionModuleActivity;
import com.pbd.ifttw.NewConditionModuleActivity;
import com.pbd.ifttw.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class NewRoutineFragment extends Fragment {
    private String LOG_TAG = "New Routine";
    private static final int CONDITION_REQUEST = 1;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_routine, container, false);
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
        return root;
    }

    private void launchNewConditionModuleActivity(@Nullable View view) {
        Log.d(LOG_TAG, "This button Clicked!");
        Intent intent = new Intent(getActivity(), NewConditionModuleActivity.class);
        startActivityForResult(intent, CONDITION_REQUEST);
    }

    private void launchNewActionModuleActivity(@Nullable View view) {
        Log.d(LOG_TAG, "What button Clicked!");
        Intent intent = new Intent(getActivity(), NewActionModuleActivity.class);
        startActivityForResult(intent, CONDITION_REQUEST);
    }
}
