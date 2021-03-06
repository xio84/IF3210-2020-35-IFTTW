package com.pbd.ifttw.ui.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pbd.ifttw.MainActivity;
import com.pbd.ifttw.R;
import com.pbd.ifttw.database.Routine;
import com.pbd.ifttw.database.SQLiteRoutineDatabaseHelper;
import com.pbd.ifttw.service.SensorBackgroundService;
import com.pbd.ifttw.service.TimerBackgroundReceiver;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.pbd.ifttw.ui.main.NewRoutineFragment.ACTION_TYPE;
import static com.pbd.ifttw.ui.main.NewRoutineFragment.ACTION_VALUE;
import static com.pbd.ifttw.ui.main.NewRoutineFragment.CONDITION_TYPE;
import static com.pbd.ifttw.ui.main.NewRoutineFragment.CONDITION_VALUE;

public class InactiveBookmarkAdapter extends RecyclerView.Adapter<InactiveBookmarkAdapter.MyViewHolder> {

    private Context context;
    private List<Routine> notesList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView routine_id_text;
        TextView routine_name_text;
        TextView routine_condition_type_text;
        TextView routine_condition_value_text;
        TextView routine_action_type_text;
        TextView routine_action_value_text;
        Button delete_routine_button;
        Button activate_button;

        MyViewHolder(View view) {
            super(view);
            routine_id_text = view.findViewById(R.id.routine_id_text);
            routine_name_text = view.findViewById(R.id.routine_name_text);
            routine_condition_type_text = view.findViewById(R.id.routine_condition_type_text);
            routine_condition_value_text = view.findViewById(R.id.routine_condition_value_text);
            routine_action_type_text = view.findViewById(R.id.routine_action_type_text);
            routine_action_value_text = view.findViewById(R.id.routine_action_value_text);
            delete_routine_button = view.findViewById(R.id.delete_inactive_routine);
            activate_button = view.findViewById(R.id.activate_button);
        }
    }


    public InactiveBookmarkAdapter(Context context, List<Routine> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_bookmark_inactive, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Routine routine = notesList.get(position);

        holder.routine_id_text.setText(routine.getId());
        holder.routine_name_text.setText(routine.getName());
        holder.routine_condition_type_text.setText(routine.getCondition_type());
        holder.routine_condition_value_text.setText(routine.getCondition_value());
        holder.routine_action_type_text.setText(routine.getAction_type());
        holder.routine_action_value_text.setText(routine.getAction_value());
        holder.delete_routine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(holder.routine_id_text.getText().toString());
                Log.d("Bookmark Adapter", "Routine " + index + " deleted");
                MainActivity parent = (MainActivity) context;
                SQLiteRoutineDatabaseHelper db = parent.getDb();
                SQLiteDatabase writableDatabase = db.getWritableDatabase();
                writableDatabase.delete("ROUTINE", "id=?", new String[]{Integer.toString(index)});
                writableDatabase.close();
                notesList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.activate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String condition_type, condition_value, action_type, action_value;
                condition_type = routine.getCondition_type();
                condition_value = routine.getCondition_value();
                action_type = routine.getAction_type();
                action_value = routine.getAction_value();
                int index = Integer.parseInt(holder.routine_id_text.getText().toString());
                Intent intent;
                PendingIntent scheduledIntent;
                AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                // add some extras for config
                Bundle args = new Bundle();
                args.putString(CONDITION_TYPE, condition_type);
                args.putString(ACTION_TYPE, action_type);
                args.putString(CONDITION_VALUE, condition_value);
                args.putString(ACTION_VALUE, action_value);
                if (condition_type.equals("proximity")) {
                    Log.d("Bookmark Adapter", "Deactivating/Activating proximity");
                    intent = new Intent(context, SensorBackgroundService.class);
                    intent.putExtras(args);
                    // try getting interval option
                    long interval;
                    interval = 1000L;

                    scheduledIntent = PendingIntent.getService(context, index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // start the service
                    scheduler.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, scheduledIntent);
                } else if (condition_type.equals(context.getString(R.string.once_timer_condition_type))){ //TODO add case for API
                    Log.d("Bookmark Adapter", "Deactivating/Activating timer");
                    intent = new Intent(context, TimerBackgroundReceiver.class);
                    intent.putExtras(args);
                    // try getting interval option
                    long interval;
                    interval = 1000L;

                    scheduledIntent = PendingIntent.getBroadcast(context, index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // start the service
                    long triggerMilis = Long.parseLong(condition_value);
                    scheduler.set(AlarmManager.RTC, triggerMilis, scheduledIntent);
                } else if (condition_type.equals(context.getString(R.string.repeating_timer_condition_type))) {
                    intent = new Intent(context.getApplicationContext(), TimerBackgroundReceiver.class);
                    intent.putExtras(args);
                    // try getting interval option
                    long interval;

                    String[] values = condition_value.split("R", 2);
                    interval = Long.parseLong(values[1]);

                    scheduledIntent = PendingIntent.getBroadcast(context.getApplicationContext(),index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // start the service
                    long triggerMilis = Long.parseLong(values[0]);
                    Log.d("Alarm set at", String.valueOf(triggerMilis));
                    scheduler.setInexactRepeating(AlarmManager.RTC, triggerMilis, interval, scheduledIntent);
                }



                Log.d("Bookmark Adapter", "Routine " + Integer.toString(index) + " activated");
                MainActivity parent = (MainActivity) context;
                SQLiteRoutineDatabaseHelper db = parent.getDb();
                SQLiteDatabase writableDatabase = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("status", "active");
                writableDatabase.update("ROUTINE", cv, "id=?", new String[]{Integer.toString(index)});
                writableDatabase.close();
                notesList.remove(position);
                notifyDataSetChanged();
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, routine.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
