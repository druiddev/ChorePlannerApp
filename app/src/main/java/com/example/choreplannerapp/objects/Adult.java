package com.example.choreplannerapp.objects;

import java.util.ArrayList;

public class Adult {

    String userId;
    String name;
    String profile;
    int color;
    String age;
    ArrayList<Child> childrenList;


    public Adult(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ArrayList<Child> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(ArrayList<Child> childrenList) {
        this.childrenList = childrenList;
    }
}
