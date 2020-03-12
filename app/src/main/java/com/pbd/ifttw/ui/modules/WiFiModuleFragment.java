package com.pbd.ifttw.ui.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.NewRoutineFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class WiFiModuleFragment extends Fragment {

    private Bundle b = new Bundle();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wifi, container, false);
        Button near = root.findViewById(R.id.onButton);
        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString(NewRoutineFragment.ACTION_TYPE, "wifi");
                b.putInt(NewRoutineFragment.ACTION_VALUE, 1);
                returnData(v);
            }
        });
        Button far = root.findViewById(R.id.offButton);
        far.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString(NewRoutineFragment.ACTION_TYPE, "wifi");
                b.putInt(NewRoutineFragment.ACTION_VALUE, 0);
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
            Log.d("WiFI Module", "Sending...");
            // Set parent activity result to RESULT_OK
            parent.setResult(Activity.RESULT_OK, replyIntent);
            // Finish parent activity
            parent.finish();
        } else {
            // Error happened, report!
            Log.d("WiFi","Can't find activity!");
        }
    }
}