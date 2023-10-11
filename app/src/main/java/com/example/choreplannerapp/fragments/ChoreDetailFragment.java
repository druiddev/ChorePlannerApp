package com.example.choreplannerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.UserListChoreBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public class ChoreDetailFragment extends Fragment {

    private static final String ARG_CHORE_DETAIL = "ARG_CHORE_DETAIL";
    ChoreInterface choreListener;

    public static ChoreDetailFragment newInstance(Chore chore) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CHORE_DETAIL, chore);

        ChoreDetailFragment fragment = new ChoreDetailFragment();
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

        Button yesButton = view.findViewById(R.id.chore_detail_yes_button);
        TextView choreDescription = view.findViewById(R.id.chore_detail_description);
        ImageView choreImage = view.findViewById(R.id.chore_detail_image);
        TextView chorePoints = view.findViewById(R.id.chore_detail_point_value);
        TextView choreRoomDescription = view.findViewById(R.id.chore_detail_room_name);



        if (getArguments() != null) {
            Chore chore = (Chore) getArguments().getSerializable(ARG_CHORE_DETAIL);

            choreDescription.setText(chore.getChoreDescription());
            choreImage.setImageResource(chore.getChoreImage());
            chorePoints.setText(String.valueOf(chore.getChorePointValue()));

            if(chore.getChoreImage() == R.drawable.kitchen){
                choreRoomDescription.setText("Kitchen");
            }
            else if(chore.getChoreImage() == R.drawable.livingroom){
                choreRoomDescription.setText("Living Room");
            }
            else if(chore.getChoreImage() == R.drawable.bathroom){
                choreRoomDescription.setText("Half Bathroom");
            }
            else if(chore.getChoreImage() == R.drawable.full_bath){
                choreRoomDescription.setText("Full Bathroom");
            }
            else if(chore.getChoreImage() == R.drawable.bedroom){
                choreRoomDescription.setText("Bedroom");
            }


            yesButton.setOnClickListener(v -> {
                AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();
                alertDialog.setTitle("Complete Chore and Delete from List");
                alertDialog.setMessage("Clicking YES sends your task to the parent or guardian on this account. Once they confirm the task is complete you will be awarded your points.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NO",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        (dialog, which) ->
                                //TODO send to parent to approve the points
                                choreListener.deleteChore(chore));

                                alertDialog.show();
            });

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chore_detail, container, false);
    }
}

