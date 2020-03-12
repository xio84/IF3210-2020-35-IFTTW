package com.pbd.ifttw.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbd.ifttw.MainActivity;
import com.pbd.ifttw.R;
import com.pbd.ifttw.database.Routine;
import com.pbd.ifttw.ui.adapter.BookmarkAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineListFragment extends Fragment {
    private View root;
    private MainActivity parent;
    private List<Routine> routines;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_routine_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        parent = (MainActivity) getActivity();
        if (parent != null) {
            routines = parent.getListRoutine();
            // Recycler View
            BookmarkAdapter adapter = new BookmarkAdapter(getContext(), routines);
            RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(parent.getApplicationContext());
            recyclerView.setLayoutManager(LayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration decoration = new DividerItemDecoration(parent.getApplicationContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(adapter);
        }
        return root;
    }
}
