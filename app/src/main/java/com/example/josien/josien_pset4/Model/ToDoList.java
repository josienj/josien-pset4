package com.example.josien.josien_pset4.Model;

import java.util.ArrayList;

/*
 * Josien Jansen
 * 11162295
 * Universiteit van Amsterdam
 */

/*
* This Activity declares the data for the to-do lists
* So you can use this in all other activities since the methods are public
 */

public class ToDoList {
    private int id = -1;
    private String title;
    private ArrayList<ToDoItem> toDoItems = new ArrayList<>();

    public ToDoList(String title) {
        this.title = title;
    }

    public ToDoList(int id, String title) {
        this.id    = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String toString() { return this.title; }
}
