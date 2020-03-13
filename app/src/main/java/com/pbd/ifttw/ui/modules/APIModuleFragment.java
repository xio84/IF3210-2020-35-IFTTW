package com.pbd.ifttw.ui.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.NewRoutineFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.pbd.ifttw.MainActivity.queue;

/**
 * A placeholder fragment containing a simple view.
 */
public class APIModuleFragment extends Fragment {

    private Bundle b = new Bundle();
    private static String APIvalue;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_api, container, false);
        final Button setAPI = root.findViewById(R.id.setAPI);
        setAPI.setEnabled(false);
        Log.d("api", "api get");
        JsonObjectRequest
                jsonObjectRequest
                = new JsonObjectRequest(
                Request.Method.GET,
                "https://opentdb.com/api.php?amount=1&category=18&difficulty=easy&type=boolean",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.println(Log.DEBUG, "api", String.valueOf(response.getJSONArray("results").getJSONObject(0).getString("question")));
                            APIvalue = "Question: ".concat(String.valueOf(response.getJSONArray("results").getJSONObject(0).getString("question"))).concat(" Answer:").concat(String.valueOf(response.getJSONArray("results").getJSONObject(0).getString("correct_answer")));
                            try {
                                APIvalue = URLDecoder.decode(APIvalue, "ISO-8859-1");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setAPI.setEnabled(true);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);
        setAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString(NewRoutineFragment.ACTION_TYPE, "api");
                b.putString(NewRoutineFragment.ACTION_VALUE, APIvalue);
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
            Log.d("API Module", "Sending...");
            // Set parent activity result to RESULT_OK
            parent.setResult(Activity.RESULT_OK, replyIntent);
            // Finish parent activity
            parent.finish();
        } else {
            // Error happened, report!
            Log.d("API Module", "Can't find activity!");
        }
    }
}