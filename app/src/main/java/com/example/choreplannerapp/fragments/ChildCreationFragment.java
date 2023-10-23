package com.example.choreplannerapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.ColorBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.interfaces.LogInInterface;
import com.example.choreplannerapp.objects.Child;
import com.example.choreplannerapp.objects.ProfileColor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChildCreationFragment extends Fragment {

    ArrayList<ProfileColor> colors = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dr = db.getReference();
    ChoreInterface choreListener;
    LogInInterface logInListener;



    public static ChildCreationFragment newInstance() {

        Bundle args = new Bundle();

        ChildCreationFragment fragment = new ChildCreationFragment();
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

        EditText childNameET = view.findViewById(R.id.child_creation_name_edit_text);
        EditText childAgeET = view.findViewById(R.id.child_creation_age_edit_text);
        GridView childColorGrid = view.findViewById(R.id.child_creation_color_chooser_grid);
        Button createChildProfile = view.findViewById(R.id.create_child_profile_button);

        populateColors();
        ColorBaseAdapter adapter = new ColorBaseAdapter(view.getContext(), colors);
        childColorGrid.setAdapter(adapter);

        Child child = new Child();


        childNameET.setOnClickListener(v -> {
           child.setName(childNameET.getText().toString());
        });

        childAgeET.setOnClickListener(v -> {
            String value = childAgeET.getText().toString();
            int finalValue = Integer.parseInt(value);
            child.setAge(finalValue);
        });

        childColorGrid.setOnItemClickListener((parent, view1, position, id) -> {
            child.setColor(colors.get(position).getColor());
        });


        createChildProfile.setOnClickListener(v -> {

            choreListener.goToMain(child);
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
        return inflater.inflate(R.layout.child_creation_layout, container, false);
    }
}
