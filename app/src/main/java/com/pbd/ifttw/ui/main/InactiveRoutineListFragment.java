package com.pbd.ifttw.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbd.ifttw.MainActivity;
import com.pbd.ifttw.R;
import com.pbd.ifttw.database.Routine;
import com.pbd.ifttw.ui.adapter.ActiveBookmarkAdapter;
import com.pbd.ifttw.ui.adapter.InactiveBookmarkAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InactiveRoutineListFragment extends Fragment {
    private View root;
    private MainActivity parent;
    private List<Routine> routines;
    private List<Routine> inactiveRoutines= new ArrayList<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_inactive_routine_list, container, false);
        parent = (MainActivity) getActivity();
        refreshList();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        parent = (MainActivity) getActivity();
        refreshList();
    }

    private void refreshList() {
        if (parent != null) {
            recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_inactive);
            routines = parent.getListRoutine();
            inactiveRoutines = new ArrayList<>();
            for (Routine r : routines) {
                if (r.getStatus().equals("inactive")) {
                    inactiveRoutines.add(r);
                }
            }

            // Recycler View
            InactiveBookmarkAdapter adapter = new InactiveBookmarkAdapter(getContext(), inactiveRoutines);
            RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(parent.getApplicationContext());
            recyclerView.setLayoutManager(LayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration decoration = new DividerItemDecoration(parent.getApplicationContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(adapter);
        }
    }
}
