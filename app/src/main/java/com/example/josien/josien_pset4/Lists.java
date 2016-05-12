package com.example.josien.josien_pset4;

/* Josien Jansen
*  11162295
*  Universiteit van Amsterdam
*/

public class Lists {

    private int _id;
    private String _todo;

    public TodoList () {

    }

    public TodoList(String _todo) {
        this._todo = _todo;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_todo(String _todo) {
        this._todo = _todo;
    }

    public int get_id() {
        return _id;
    }

    public String get_todo() {
        return _todo;
    }

    public String toString() {
        return _todo;
    }
}


