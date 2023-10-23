package com.example.choreplannerapp.interfaces;

import com.example.choreplannerapp.objects.Child;
import com.example.choreplannerapp.objects.Chore;

public interface ChoreInterface {

    void getChoresFromPicker(Chore chore);
    void openChoreDetail(Chore chore);
    void deleteChore(Chore chore);

    void openChildCreation();

    void goToMain(Child child);
}
