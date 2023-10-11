package com.example.choreplannerapp.objects;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class User {

    boolean isChild;
    Drawable userImage;
    Color userColor;
    String name;
    ArrayList<Chore> chores;
    int points;

    public User(boolean isChild, Drawable userImage, Color color, String name, ArrayList<Chore> chores, int points) {
        this.isChild = isChild;
        this.userImage = userImage;
        this.userColor = color;
        this.name = name;
        this.chores = chores;
        this.points = points;
    }

    public boolean isChild() {return isChild;}

    public Drawable getUserImage() {
        return userImage;
    }

    public Color getUserColor() {
        return userColor;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Chore> getChores() {
        return chores;
    }

    public int getPoints() {
        return points;
    }
}
