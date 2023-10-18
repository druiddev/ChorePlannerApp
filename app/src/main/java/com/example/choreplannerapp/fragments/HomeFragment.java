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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private static final String ARG__HOME_CHORES = "ARG_HOME_CHORES";
    ChoreInterface choreListener;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(currentUser.getUid());
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dr = db.getReference();


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


            if (getArguments() != null) {

                ArrayList<Chore> personalChores = (ArrayList<Chore>) getArguments().getSerializable(ARG__HOME_CHORES);
                HomeChoreBaseAdapter adapter = new HomeChoreBaseAdapter(view.getContext(), personalChores);
                GridView homeChoreList = view.findViewById(R.id.child_home_chore_grid);
                homeChoreList.setAdapter(adapter);

                database.getReference().child("Users").child(currentUser.getUid()).child("chores").setValue(personalChores);


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

