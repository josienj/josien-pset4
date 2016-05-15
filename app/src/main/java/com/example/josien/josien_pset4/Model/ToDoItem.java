package com.example.josien.josien_pset4.Model;

/*
 * Josien Jansen
 * 11162295
 * Universiteit van Amsterdam
 */

/*
* This Activity declares the data for the to-do items
* So you can use this in all other activities since the methods are public
* It includes the title and id which can be parsed through getters and setters
* Also the state of the item (done or not done) is included.
 */
public class ToDoItem {
    private int id = -1;
    private int listId;
    private String title;
    private boolean completed;

    public ToDoItem() {}

    public ToDoItem(String title) {
        this.title = title;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toString() { return this.title; }
}
