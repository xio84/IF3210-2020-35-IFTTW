package com.pbd.ifttw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.NewRoutineFragment;

import static com.pbd.ifttw.MainActivity.notificationManager;

public class TimerBackgroundReceiver extends BroadcastReceiver {
    private static final String TAG = TimerBackgroundReceiver.class.getSimpleName();
    private Bundle args;
    private String action_type, action_value;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "it's time!");
        args = intent.getExtras();

        // get some properties from the intent
        if (args != null) {
            action_type = args.getString(NewRoutineFragment.ACTION_TYPE, "NONE");
            action_value = args.getString(NewRoutineFragment.ACTION_VALUE, "NONE");
        }

        if (action_type.equals("wifi")) {
            if (action_value.equals("0")) {
                Log.d(TAG, "It's time to turn off wifi");
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);
            } else {
                Log.d(TAG, "It's time to turn on wifi");
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
            }
        }
        if (action_type.equals("notify")) {
            Log.d("txtt", "recip...");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_me")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("NotifyME")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(action_value))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(69, builder.build());

        }
        if (action_type.equals("api")) {
            Log.d("txtt", "recip...");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "api")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("API")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(action_value))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(70, builder.build());

        }

    }
}
