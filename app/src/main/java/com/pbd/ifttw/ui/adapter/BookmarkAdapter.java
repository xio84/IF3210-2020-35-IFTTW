package com.pbd.ifttw.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pbd.ifttw.R;
import com.pbd.ifttw.database.Routine;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {

    private Context context;
    private List<Routine> notesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView routine_id_text;
        public TextView routine_name_text;
        public TextView routine_condition_type_text;
        public TextView routine_condition_value_text;
        public TextView routine_action_type_text;
        public TextView routine_action_value_text;

        public MyViewHolder(View view) {
            super(view);
            routine_id_text = view.findViewById(R.id.routine_id_text);
            routine_name_text = view.findViewById(R.id.routine_name_text);
            routine_condition_type_text = view.findViewById(R.id.routine_condition_type_text);
            routine_condition_value_text = view.findViewById(R.id.routine_condition_value_text);
            routine_action_type_text = view.findViewById(R.id.routine_action_type_text);
            routine_action_value_text = view.findViewById(R.id.routine_action_value_text);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Routine routine = notesList.get(position);

        holder.routine_id_text.setText(routine.getId());
        holder.routine_name_text.setText(routine.getName());
        holder.routine_condition_type_text.setText(routine.getCondition_type());
        holder.routine_condition_value_text.setText(routine.getCondition_value());
        holder.routine_action_type_text.setText(routine.getAction_type());
        holder.routine_action_value_text.setText(routine.getAction_value());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, routine.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
