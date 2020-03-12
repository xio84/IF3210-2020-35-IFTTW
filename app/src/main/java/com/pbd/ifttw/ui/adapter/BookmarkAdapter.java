package com.pbd.ifttw.ui.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pbd.ifttw.MainActivity;
import com.pbd.ifttw.R;
import com.pbd.ifttw.database.Routine;
import com.pbd.ifttw.database.SQLiteRoutineDatabaseHelper;
import com.pbd.ifttw.service.SensorBackgroundService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {

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

        MyViewHolder(View view) {
            super(view);
            routine_id_text = view.findViewById(R.id.routine_id_text);
            routine_name_text = view.findViewById(R.id.routine_name_text);
            routine_condition_type_text = view.findViewById(R.id.routine_condition_type_text);
            routine_condition_value_text = view.findViewById(R.id.routine_condition_value_text);
            routine_action_type_text = view.findViewById(R.id.routine_action_type_text);
            routine_action_value_text = view.findViewById(R.id.routine_action_value_text);
            delete_routine_button = view.findViewById(R.id.delete_routine);
        }
    }


    public BookmarkAdapter(Context context, List<Routine> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_bookmark, parent, false);

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
                if (holder.routine_condition_type_text.getText().toString().equals("proximity")) {
                    Log.d("Bookmark Adapter", "Deleting proximity");
                }
                Integer index = Integer.parseInt(holder.routine_id_text.getText().toString());
                Intent intent = new Intent(context, SensorBackgroundService.class);
                AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                PendingIntent scheduledIntent = PendingIntent.getService(context.getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                scheduler.cancel(scheduledIntent);
                scheduledIntent.cancel();
                Log.d("Bookmark Adapter", "Routine " + index.toString() + " deleted");
                MainActivity parent = (MainActivity) context;
                SQLiteRoutineDatabaseHelper db = parent.getDb();
                SQLiteDatabase writableDatabase = db.getWritableDatabase();
                writableDatabase.delete("ROUTINE", "id=?", new String[]{index.toString()});
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
