package com.example.choreplannerapp.objects;

import java.io.Serializable;

public class Chore implements Serializable {

    int choreImage;
    String choreDescription;
    int chorePointValue;

    public Chore(int choreImage, String choreDescription, int chorePointValue) {
        this.choreImage = choreImage;
        this.choreDescription = choreDescription;
        this.chorePointValue = chorePointValue;
    }

    public int getChoreImage() {
        return choreImage;
    }

    public String getChoreDescription() {
        return choreDescription;
    }

    public int getChorePointValue() {
        return chorePointValue;
    }
}
