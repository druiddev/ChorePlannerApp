package com.example.choreplannerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.ChoreBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public class ChoreFragment extends Fragment {

    private static final String ARG_CHORES = "ARG_CHORES";
    ArrayList<Chore> kitchenChores = new ArrayList<>();
    ArrayList<Chore> livingChores = new ArrayList<>();
    ArrayList<Chore> halfBathChores = new ArrayList<>();
    ArrayList<Chore> fullBathChores = new ArrayList<>();
    ArrayList<Chore> bedroomChores = new ArrayList<>();

    ChoreInterface choreListener;

    public static ChoreFragment newInstance(ArrayList<ArrayList<Chore>> listOfChoreLists) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CHORES, listOfChoreLists);
        ChoreFragment fragment = new ChoreFragment();
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

            ArrayList<ArrayList<Chore>> chores = (ArrayList<ArrayList<Chore>>) getArguments().getSerializable(ARG_CHORES);

            kitchenChores.addAll(chores.get(0));
            livingChores.addAll(chores.get(1));
            halfBathChores.addAll(chores.get(2));
            fullBathChores.addAll(chores.get(3));
            bedroomChores.addAll(chores.get(4));

            ChoreBaseAdapter kitchenAdapter = new ChoreBaseAdapter(view.getContext(), kitchenChores);
            ChoreBaseAdapter livingAdapter = new ChoreBaseAdapter(view.getContext(), livingChores);
            ChoreBaseAdapter halfBathAdapter = new ChoreBaseAdapter(view.getContext(), halfBathChores);
            ChoreBaseAdapter fullBathAdapter = new ChoreBaseAdapter(view.getContext(), fullBathChores);
            ChoreBaseAdapter bedroomAdapter = new ChoreBaseAdapter(view.getContext(), bedroomChores);

            GridView kitchenGrid = requireView().findViewById(R.id.chore_grid_kitchen);
            GridView livingGrid = requireView().findViewById(R.id.chore_grid_living);
            GridView halfBathGrid = requireView().findViewById(R.id.chore_grid_half_bath);
            GridView fullBathGrid = requireView().findViewById(R.id.chore_grid_full_bath);
            GridView bedroomGrid = requireView().findViewById(R.id.chore_grid_bedroom);

            kitchenGrid.setAdapter(kitchenAdapter);
            livingGrid.setAdapter(livingAdapter);
            halfBathGrid.setAdapter(halfBathAdapter);
            fullBathGrid.setAdapter(fullBathAdapter);
            bedroomGrid.setAdapter(bedroomAdapter);

            kitchenGrid.setOnItemClickListener((parent, view1, position, id) -> {
                choreListener.getChoresFromPicker(kitchenChores.get(position));

            });

            livingGrid.setOnItemClickListener((parent, view1, position, id) -> {
                choreListener.getChoresFromPicker(livingChores.get(position));

            });

            halfBathGrid.setOnItemClickListener((parent, view1, position, id) -> {
                choreListener.getChoresFromPicker(halfBathChores.get(position));

            });

            fullBathGrid.setOnItemClickListener((parent, view1, position, id) -> {
                choreListener.getChoresFromPicker(fullBathChores.get(position));

            });

            bedroomGrid.setOnItemClickListener((parent, view1, position, id) -> {
                choreListener.getChoresFromPicker(bedroomChores.get(position));

            });

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chore_grid, container, false);
    }


}
