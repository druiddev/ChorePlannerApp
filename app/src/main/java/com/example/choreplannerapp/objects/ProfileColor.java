package com.example.choreplannerapp.objects;

import android.graphics.Color;

public class ProfileColor {

    String name;
    int color;

    public ProfileColor(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
