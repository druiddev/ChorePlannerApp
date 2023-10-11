package com.example.choreplannerapp.interfaces;

import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public interface ChoreInterface {

    void getChoresFromPicker(Chore chore);
    void openChoreDetail(Chore chore);
    void deleteChore(Chore chore);
}
