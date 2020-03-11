package com.pbd.ifttw.ui.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.PageViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * A placeholder fragment containing a simple view.
 */
public class SensorsModuleFragment extends Fragment {
    public static final String CONDITION_TYPE = "condition_type";
    public static final String CONDITION_VALUE = "condition_value";
    private Bundle b = new Bundle();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sensors, container, false);
        Button near = root.findViewById(R.id.nearButton);
        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString(CONDITION_TYPE, "proximity");
                b.putInt(CONDITION_VALUE, 0);
                returnData(v);
            }
        });
        Button far = root.findViewById(R.id.farButton);
        far.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString(CONDITION_TYPE, "proximity");
                b.putInt(CONDITION_VALUE, 1);
                returnData(v);
            }
        });
        return root;
    }

    private void returnData(@Nullable View v) {
        Intent replyIntent = new Intent();
        // Put extra data
        replyIntent.putExtras(b);
        // Get parent activity
        Activity parent = getActivity();
        if (parent != null) {
            Log.d("Sensors Module", "Sending...");
            // Set parent activity result to RESULT_OK
            parent.setResult(Activity.RESULT_OK, replyIntent);
            // Finish parent activity
            parent.finish();
        } else {
            // Error happened, report!
            Log.d("Sensors","Can't find activity!");
        }
    }
}