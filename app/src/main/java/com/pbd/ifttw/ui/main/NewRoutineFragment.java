package com.pbd.ifttw.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pbd.ifttw.MainActivity;
import com.pbd.ifttw.NewActionModuleActivity;
import com.pbd.ifttw.NewConditionModuleActivity;
import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.modules.SensorsModuleFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class NewRoutineFragment extends Fragment {
    private String LOG_TAG = "New Routine";
    public static final int CONDITION_REQUEST = 1;
    public static final int ACTION_REQUEST = 2;
    private View root;

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
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity parent = (MainActivity) getActivity();
        if (parent != null) {
            Bundle data_bundle = parent.getData_bundle();
            Button thisButton = root.findViewById(R.id.thisButton);
            if (data_bundle != null &&
                    !data_bundle.
                            getString(SensorsModuleFragment.CONDITION_TYPE, "NONE")
                            .equals("NONE")) {
                String valString;
                int val = data_bundle.getInt(SensorsModuleFragment.CONDITION_VALUE, -1);
                switch (val) {
                    case 0:
                        valString = "Near";
                        break;
                    case 1:
                        valString = "Far";
                        break;
                    default:
                        valString = getResources().getString(R.string.wrong);
                }
                String message = data_bundle.getString(SensorsModuleFragment.CONDITION_TYPE,
                        getResources().getString(R.string.something)) + " " +
                        getResources().getString(R.string.is) + " " +
                        valString;
                thisButton.setText(message);
            }
        } else {
            Log.d("New Routine Fragment", "Parent is Null!");
        }
    }

    private void launchNewConditionModuleActivity(@Nullable View view) {
        Log.d(LOG_TAG, "This button Clicked!");
        Intent intent = new Intent(getActivity(), NewConditionModuleActivity.class);
        Activity parent = getActivity();
        if (parent != null) {
            parent.startActivityForResult(intent, CONDITION_REQUEST);
        } else {
            Log.d("New Routine Fragment", "Can't getActivity()");
        }
    }

    private void launchNewActionModuleActivity(@Nullable View view) {
        Log.d(LOG_TAG, "What button Clicked!");
        Intent intent = new Intent(getActivity(), NewActionModuleActivity.class);
        Activity parent = getActivity();
        if (parent != null) {
            parent.startActivityForResult(intent, ACTION_REQUEST);
        } else {
            Log.d("New Routine Fragment", "Can't getActivity()");
        }
    }
}
