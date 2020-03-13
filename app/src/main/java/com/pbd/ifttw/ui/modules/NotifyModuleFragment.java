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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.NewRoutineFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotifyModuleFragment extends Fragment {

    private Bundle b = new Bundle();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notify, container, false);
        Button setNotify = root.findViewById(R.id.setNotifyText);
        final TextView notifyText = root.findViewById(R.id.notify_text);
        setNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString(NewRoutineFragment.ACTION_TYPE, "notify");
                b.putString(NewRoutineFragment.ACTION_VALUE, notifyText.getText().toString());
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
            Log.d("Notify Me Module", "Sending...");
            // Set parent activity result to RESULT_OK
            parent.setResult(Activity.RESULT_OK, replyIntent);
            // Finish parent activity
            parent.finish();
        } else {
            // Error happened, report!
            Log.d("Notify Me Module","Can't find activity!");
        }
    }
}