package com.example.josien.josien_pset4.Model;

import java.util.ArrayList;

/*
 * Josien Jansen
 * 11162295
 * Universiteit van Amsterdam
 */
public class ToDoList {
    private int id = -1;
    private String title;
    private ArrayList<ToDoItem>  toDoItems = new ArrayList<ToDoItem>();

    public ToDoList() {}

    public ToDoList(String title) {
        this.title = title;
    }

    public ToDoList(int id, String title) {
        this.id    = id;
        this.title = title;
    }

    public ToDoList(int id, String title, ArrayList<ToDoItem> toDoItems) {
        this.id        = id;
        this.title     = title;
        this.toDoItems = toDoItems;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ToDoItem> getToDoItems() {
        return this.toDoItems;
    }

    public void setToDoItems(ArrayList<ToDoItem> toDoItems) {
        this.toDoItems = toDoItems;
    }

    public void addToDoItem(ToDoItem toDoItem) {
        this.toDoItems.add(toDoItem);
    }

    public void removeToDoItem(ToDoItem toDoItem) {
        this.toDoItems.remove(toDoItem);
    }

    public String toString() { return this.title; }
}
