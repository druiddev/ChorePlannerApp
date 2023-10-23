package com.example.choreplannerapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.ChildWeekAdapter;
import com.example.choreplannerapp.adapters.HomeChoreBaseAdapter;
import com.example.choreplannerapp.adapters.UserBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Adult;
import com.example.choreplannerapp.objects.Child;
import com.example.choreplannerapp.objects.Chore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String ARG_HOME_CHORES = "ARG_HOME_CHORES";
    private static final String ARG_USERS = "ARG_USERS";
    ChoreInterface choreListener;
    ArrayList<Chore> emptyChores = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private final DatabaseReference dr = db.getReference("Users");


    public static HomeFragment newInstance(ArrayList<Chore> personalChores, ArrayList<Child> children) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_HOME_CHORES, personalChores);
        args.putSerializable(ARG_USERS, children);

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

            ArrayList<Chore> personalChores = (ArrayList<Chore>) getArguments().getSerializable(ARG_HOME_CHORES);
            HomeChoreBaseAdapter adapter = new HomeChoreBaseAdapter(view.getContext(), personalChores);
            GridView homeChoreList = view.findViewById(R.id.child_home_chore_grid);
            homeChoreList.setAdapter(adapter);



            homeChoreList.setOnItemClickListener((parent, view1, position, id) -> {
                choreListener.openChoreDetail(personalChores.get(position));

            });


            ArrayList<Child> children = (ArrayList<Child>) getArguments().getSerializable(ARG_USERS);

            UserBaseAdapter userBaseAdapter = new UserBaseAdapter(view.getContext(), children);
            GridView usersGrid = view.findViewById(R.id.grid_adult_home_family);
            usersGrid.setAdapter(userBaseAdapter);


            database.getReference().child("Users").child(currentUser.getUid())
                    .child("childrenList").setValue(children);
        }
    }

        @Nullable
        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
            return inflater.inflate(R.layout.adult_home, container, false);
        }







    }

