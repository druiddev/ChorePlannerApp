package com.example.choreplannerapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.ColorBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.interfaces.LogInInterface;
import com.example.choreplannerapp.objects.ProfileColor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileCreationFragment extends Fragment {

    ArrayList<ProfileColor> colors = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dr = db.getReference();
    ChoreInterface choreListener;
    LogInInterface logInListener;



    public static ProfileCreationFragment newInstance() {

        Bundle args = new Bundle();

        ProfileCreationFragment fragment = new ProfileCreationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ChoreInterface){
            choreListener = (ChoreInterface) context;
        } else if (context instanceof LogInInterface){
            logInListener = (LogInInterface) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button adultButton = view.findViewById(R.id.adult_button);
        Button childButton = view.findViewById(R.id.child_button);
        Button createProfile = view.findViewById(R.id.create_profile_button);

        adultButton.setOnClickListener(v -> {
            database.getReference().child("Users").child(currentUser.getUid()).child("age").setValue("Adult");
        });

        childButton.setOnClickListener(v -> {
            database.getReference().child("Users").child(currentUser.getUid()).child("age").setValue("Child");
        });

        createProfile.setOnClickListener(v -> {
            logInListener.goToMain();
        });

        populateColors();
        ColorBaseAdapter adapter = new ColorBaseAdapter(view.getContext(), colors);
        GridView colorGrid = view.findViewById(R.id.color_chooser_grid);
        colorGrid.setAdapter(adapter);

        colorGrid.setOnItemClickListener((parent, view1, position, id) -> {

            database.getReference().child("Users").child(currentUser.getUid())
                    .child("color").setValue(colors.get(position).getColor());

        });
    }

    private void populateColors() {
        colors.add(new ProfileColor("White", Color.WHITE));
        colors.add(new ProfileColor("Black", Color.BLACK));
        colors.add(new ProfileColor("Red", Color.RED));
        colors.add(new ProfileColor("Yellow", Color.YELLOW));
        colors.add(new ProfileColor("Green", Color.GREEN));
        colors.add(new ProfileColor("Blue", Color.BLUE));
        colors.add(new ProfileColor("Magenta", Color.MAGENTA));
        colors.add(new ProfileColor("Gray", Color.GRAY));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_creation_layout, container, false);
    }
}
