package com.example.choreplannerapp.objects;

import java.util.ArrayList;

public class Child {

    String name;
    int age;
    int color;
    ArrayList<Chore> chores;

    public Child(String name, int age, int color, ArrayList<Chore> chores) {
        this.name = name;
        this.age = age;
        this.color = color;
        this.chores = chores;
    }

    public Child() {
    }

    public Child(String name, int age, int color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Chore> getChores() {
        return chores;
    }

    public void setChores(ArrayList<Chore> chores) {
        this.chores = chores;
    }
}
