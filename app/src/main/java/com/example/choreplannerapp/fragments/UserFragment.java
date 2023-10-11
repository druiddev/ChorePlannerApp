package com.example.choreplannerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.ChoreBaseAdapter;
import com.example.choreplannerapp.adapters.UserListChoreBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private static final String ARG_CHORE = "ARG_CHORE";
    ChoreInterface choreListener;

    public static UserFragment newInstance(ArrayList<Chore> personalChores) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CHORE, personalChores);

        UserFragment fragment = new UserFragment();
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

        if (getArguments() != null) {

            ArrayList<Chore> personalChores = (ArrayList<Chore>) getArguments().getSerializable(ARG_CHORE);
            UserListChoreBaseAdapter adapter = new UserListChoreBaseAdapter(view.getContext(), personalChores);
            ListView personalChoreList = view.findViewById(R.id.user_chore_list);
            personalChoreList.setAdapter(adapter);


            personalChoreList.setOnItemClickListener((parent, view1, position, id) -> {
                choreListener.openChoreDetail(personalChores.get(position));

            });

        }
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_layout, container, false);
    }
}
