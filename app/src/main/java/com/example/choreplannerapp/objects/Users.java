package com.example.choreplannerapp.objects;

import java.util.ArrayList;

public class Users {

    String userId;
    String name;
    String profile;

    ArrayList<Chore> chores;

    public Users(String userId, String name, String profile, ArrayList<Chore> chores) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.chores = chores;
    }

    public Users(){

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setChores(ArrayList<Chore> chores) {
        this.chores = chores;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public ArrayList<Chore> getChores() {
        return chores;
    }
}
