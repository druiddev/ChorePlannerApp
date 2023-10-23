package com.example.choreplannerapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.fragments.ChildCreationFragment;
import com.example.choreplannerapp.fragments.SettingsFragment;
import com.example.choreplannerapp.fragments.UserAdultFragment;
import com.example.choreplannerapp.fragments.WheelFragment;
import com.example.choreplannerapp.fragments.BankFragment;
import com.example.choreplannerapp.fragments.ChoreDetailFragment;
import com.example.choreplannerapp.fragments.ChoreFragment;
import com.example.choreplannerapp.fragments.HomeFragment;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Adult;
import com.example.choreplannerapp.objects.Child;
import com.example.choreplannerapp.objects.Chore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ChoreInterface {

    BottomNavigationView navigationView;
    ArrayList<Chore> kitchen = new ArrayList<>();
    ArrayList<Chore> bathroom = new ArrayList<>();
    ArrayList<Chore> livingRoom = new ArrayList<>();
    ArrayList<Chore> halfBath = new ArrayList<>();
    ArrayList<Chore> bedroom = new ArrayList<>();
    ArrayList<ArrayList<Chore>> listOfChoreLists = new ArrayList<>();
    ArrayList<Chore> personalChores = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(currentUser.getUid());
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dr = db.getReference("Users");

    ArrayList<Adult> adults = new ArrayList<>();
    Adult mainAdult;

    ArrayList<Child> children = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLists();

        getData();



//TODO WRONG BUT CLOSE
        dr.child(currentUser.getUid()).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());

                    for(DataSnapshot child: task.getResult().getChildren()){
                        Adult adult = child.getValue(Adult.class);
                        adults.add(adult);
                        mainAdult = adult;
                        assert mainAdult != null;
                        children = mainAdult.getChildrenList();
                    }

                }
            else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
            }
        });


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance(personalChores, children))
                .addToBackStack("home")
                .commit();




        navigationView = findViewById(R.id.navigationView);
        navigationView.setSelectedItemId(R.id.home);


        navigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:

                    database.getReference().child("Users")
                            .child(currentUser.getUid()).child("chores").setValue(personalChores);


                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment.newInstance(personalChores, children))
                            .commit();
                    return true;

                case R.id.piggyBank:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, BankFragment.newInstance())
                            .commit();
                    return true;

                case R.id.wheel:

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, WheelFragment.newInstance())
                                .commit();
                        return true;

                case R.id.chorePicker:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, ChoreFragment.newInstance(listOfChoreLists))
                            .commit();
                    return true;

            }
            return false;

        });

    }


    public void populateLists(){

        //these lists are the general start. if they have customized lists this will not work anymore, this is for resets and new app users.


        kitchen.add(new Chore(R.drawable.kitchen, "Dishwasher", 50));
        kitchen.add(new Chore(R.drawable.kitchen, "Wipe Out Microwave", 15));
        kitchen.add(new Chore(R.drawable.kitchen, "Wipe Down Counters", 35));
        kitchen.add(new Chore(R.drawable.kitchen, "Mop Kitchen", 75));
        kitchen.add(new Chore(R.drawable.kitchen, "Wipe Out Sinks", 10));
        kitchen.add(new Chore(R.drawable.kitchen, "Sweep/Vacuum Kitchen", 75));
        kitchen.add(new Chore(R.drawable.kitchen, "Wipe Down Cabinets", 25));
        kitchen.add(new Chore(R.drawable.kitchen, "Organize The Cabinets", 75));
        kitchen.add(new Chore(R.drawable.kitchen, "Windex Back Door", 25));

        livingRoom.add(new Chore(R.drawable.livingroom, "Sweep Living Room", 75));
        livingRoom.add(new Chore(R.drawable.livingroom, "Mop Living Room", 75));
        livingRoom.add(new Chore(R.drawable.livingroom, "Vacuum Living Room", 75));
        livingRoom.add(new Chore(R.drawable.livingroom, "Dust Desks/Stands", 15));
        livingRoom.add(new Chore(R.drawable.livingroom, "Fix Couch Pillows/Blankets", 15));
        livingRoom.add(new Chore(R.drawable.livingroom, "Windex Front Door", 25));
        livingRoom.add(new Chore(R.drawable.livingroom, "Put Shoes Away", 5));
        livingRoom.add(new Chore(R.drawable.livingroom, "Put Toys Away", 15));
        livingRoom.add(new Chore(R.drawable.livingroom, "Water Plants", 5));
        livingRoom.add(new Chore(R.drawable.livingroom, "Pick Up Nuggets", 5));
        livingRoom.add(new Chore(R.drawable.livingroom, "Feed/Water Animals", 25));

        halfBath.add(new Chore(R.drawable.bathroom, "Sweep Half Bath", 15));
        halfBath.add(new Chore(R.drawable.bathroom, "Mop Half Bath", 15));
        halfBath.add(new Chore(R.drawable.bathroom, "Clean Toilet", 25));
        halfBath.add(new Chore(R.drawable.bathroom, "Clean Sink", 15));
        halfBath.add(new Chore(R.drawable.bathroom, "Replace Soaps", 10));
        halfBath.add(new Chore(R.drawable.bathroom, "Organize Drawers", 15));
        halfBath.add(new Chore(R.drawable.bathroom, "Dust Shelves", 15));
        halfBath.add(new Chore(R.drawable.bathroom, "Windex Mirror", 15));
        halfBath.add(new Chore(R.drawable.bathroom, "Replace Toiletries", 25));

        bathroom.add(new Chore(R.drawable.full_bath, "Sweep Half Bath", 15));
        bathroom.add(new Chore(R.drawable.full_bath, "Mop Half Bath", 15));
        bathroom.add(new Chore(R.drawable.full_bath, "Clean Toilet", 25));
        bathroom.add(new Chore(R.drawable.full_bath, "Clean Sink", 15));
        bathroom.add(new Chore(R.drawable.full_bath, "Replace Soaps", 10));
        bathroom.add(new Chore(R.drawable.full_bath, "Organize Drawers", 15));
        bathroom.add(new Chore(R.drawable.full_bath, "Dust Shelves", 15));
        bathroom.add(new Chore(R.drawable.full_bath, "Windex Mirror", 15));
        bathroom.add(new Chore(R.drawable.full_bath, "Replace Toiletries", 25));
        bathroom.add(new Chore(R.drawable.full_bath, "Clean Shower", 45));
        bathroom.add(new Chore(R.drawable.full_bath, "Organize Shower", 15));
        bathroom.add(new Chore(R.drawable.full_bath, "Replace Towels", 25));

        bedroom.add(new Chore(R.drawable.bedroom, "Vacuum Bedroom", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Make Bed", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Clean Bedroom Floor", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Dust Tables/Shelves", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Organize Drawers", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Put away Laundry", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Dust Tables", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Windex Windows", 75));
        bedroom.add(new Chore(R.drawable.bedroom, "Organize Toys", 75));


        listOfChoreLists.add(kitchen);
        listOfChoreLists.add(livingRoom);
        listOfChoreLists.add(halfBath);
        listOfChoreLists.add(bathroom);
        listOfChoreLists.add(bedroom);

    }

    @Override
    public void getChoresFromPicker(Chore chore) {
        personalChores.add(chore);

    }

    @Override
    public void openChoreDetail(Chore chore){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ChoreDetailFragment.newInstance(chore))
                .addToBackStack("choreDetail")
                .commit();
    }

    @Override
    public void deleteChore(Chore chore) {

        personalChores.remove(chore);

        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void openChildCreation() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ChildCreationFragment.newInstance())
                .addToBackStack("childCreation")
                .commit();
    }

    @Override
    public void goToMain(Child child) {

        children.add(child);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance(personalChores, children))
                .addToBackStack("home")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_menu_button:

                if(currentUser != null) {
                    Log.v("GOOGLETAG", "Account token: MAIN : " + currentUser.getUid());
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, UserAdultFragment.newInstance())
                        .addToBackStack("adultUser")
                        .commit();


                break;
            case R.id.settings_menu_button:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, SettingsFragment.newInstance())
                        .addToBackStack("settings")
                        .commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }










    private void getData() {


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Iterable<DataSnapshot> children = snapshot.getChildren();

                for(DataSnapshot child: children){
                    Adult user = child.getValue(Adult.class);
                    adults.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FIREBASEERROR", "onCancelled: " + error);

            }
        });
    }


}