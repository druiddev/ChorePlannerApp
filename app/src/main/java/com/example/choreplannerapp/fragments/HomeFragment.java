package com.example.choreplannerapp.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.ChildWeekAdapter;
import com.example.choreplannerapp.adapters.HomeChoreBaseAdapter;
import com.example.choreplannerapp.adapters.UserListChoreBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Chore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private static final String ARG__HOME_CHORES = "ARG_HOME_CHORES";
    ChoreInterface choreListener;

    public static HomeFragment newInstance(ArrayList<Chore> personalChores) {

        Bundle args = new Bundle();
        args.putSerializable(ARG__HOME_CHORES, personalChores);

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ChoreInterface){
            choreListener = (ChoreInterface) context;
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> daysOfWeek = new ArrayList<>();
        daysOfWeek.add("Monday");
        daysOfWeek.add("Tuesday");
        daysOfWeek.add("Wednesday");
        daysOfWeek.add("Thursday");
        daysOfWeek.add("Friday");
        daysOfWeek.add("Saturday");
        daysOfWeek.add("Sunday");

        ChildWeekAdapter weekAdapter = new ChildWeekAdapter(view.getContext(), daysOfWeek);
        GridView weekDays = view.findViewById(R.id.child_home_week_days);
        weekDays.setAdapter(weekAdapter);


//        FloatingActionButton addNoteButton = view.findViewById(R.id.home_note_add_button);
//        TextView notePad = view.findViewById(R.id.home_note_pad);
//
//        //TODO setvisibility gone if user is a child
//        addNoteButton.setOnClickListener(v -> {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
//            final View noteLayout = getLayoutInflater().inflate(R.layout.note_layout, null);
//            builder.setView(noteLayout);
//            builder.setTitle("Note To Child");
//            EditText editText = noteLayout.findViewById(R.id.edit_text);
//
//            builder.setPositiveButton("POST NOTE", (dialog, which) -> notePad.setText(editText.getText()));
//
//            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
//
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        });

            if (getArguments() != null) {

                ArrayList<Chore> personalChores = (ArrayList<Chore>) getArguments().getSerializable(ARG__HOME_CHORES);
                HomeChoreBaseAdapter adapter = new HomeChoreBaseAdapter(view.getContext(), personalChores);
                GridView homeChoreList = view.findViewById(R.id.child_home_chore_grid);
                homeChoreList.setAdapter(adapter);


                homeChoreList.setOnItemClickListener((parent, view1, position, id) -> {
                    choreListener.openChoreDetail(personalChores.get(position));

                });
            }
    }

        @Nullable
        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
            return inflater.inflate(R.layout.child_home, container, false);
        }
    }

