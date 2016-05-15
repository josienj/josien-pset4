package com.example.josien.josien_pset4;

/*
*  Josien Jansen
*  11162295
*  Universiteit van Amsterdam
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import com.example.josien.josien_pset4.Model.ToDoItem;
import com.example.josien.josien_pset4.Model.ToDoList;

import java.util.ArrayList;

/*
*  Create a SQLdatabase
*/
public class ToDoManager extends SQLiteOpenHelper {

    private static ToDoManager instance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo.db";
    public static final String TABLE_LIST = "todo_list";
    public static final String TABLE_ITEM = "todo_item";

    public static synchronized ToDoManager getInstance(Context context) {
        if (instance == null) {
            instance = new ToDoManager(context.getApplicationContext());
        }
        return instance;
    }

    public ToDoManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createListTableQuery = "CREATE TABLE " + TABLE_LIST + "("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "title TEXT "+
                ")";

        String createItemTableQuery = "CREATE TABLE " + TABLE_ITEM + "("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "list_id INTEGER, "+
                "title TEXT, "+
                "is_completed INTEGER"+
                ")";
        db.execSQL(createListTableQuery);
        db.execSQL(createItemTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_LIST);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ITEM);
    }

    /*
    * Add a new row to the database
    */
    public void saveToDoList(ToDoList toDoList) {
        ContentValues values = new ContentValues();
        values.put("title", toDoList.getTitle());

        SQLiteDatabase db = getWritableDatabase();
        // If the id is lower the 0, the item doesn't exist yet in the database.
        if (toDoList.getId() < 0) {
            long listId = db.insert(TABLE_LIST, null, values);
            toDoList.setId(((int) listId));
        } else {
            db.update(TABLE_LIST, values, "id = ?", new String[] {String.valueOf(toDoList.getId())});
        }

        db.close();
    }

    /*
    * Add a new row to the database
    */
    public void saveToDoItem(ToDoItem toDoItem) {
        ContentValues values = new ContentValues();
        values.put("title", toDoItem.getTitle());
        values.put("list_id", toDoItem.getListId());
        values.put("is_completed", (toDoItem.isCompleted()) ? 1 : 0);

        SQLiteDatabase db = getWritableDatabase();
        // If the id is lower the 0, the item doesn't exist yet in the database.
        if (toDoItem.getId() < 0) {
            long itemId = db.insert(TABLE_ITEM, null, values);
            toDoItem.setId(((int) itemId));
        } else {
            db.update(TABLE_ITEM, values, "id = ?", new String[] {String.valueOf(toDoItem.getId())});
        }

        db.close();
    }


    /*
    * Delete a list from the database
    */
    public void deleteList (int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LIST, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /*
    * Delete an item from the database
    */
    public void deleteItem (int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ITEM, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /*
    * Get all todolists from the database
    */
    public ArrayList<ToDoList> retrieveTodoLists(){
        ArrayList<ToDoList> toDoLists = new ArrayList<ToDoList>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LIST + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        // Move to the first row in your results
        if (c.moveToFirst()) {
            // Loop through all results
            do {
                // Create a new todoList object and set the correct data
                ToDoList list = new ToDoList(c.getInt(0), c.getString(1));

                toDoLists.add(list);
            } while (c.moveToNext()) ;
        }

        db.close();
        c.close();
        return toDoLists;
    }

    /*
    * Get all todolist items from the database
    */
    public ArrayList<ToDoItem> retrieveTodoItemsByListId(int listId){
        ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEM + " WHERE list_id = "+ Integer.valueOf(listId).toString();

        Cursor c = db.rawQuery(query, null);

        // Move to the first row in your results
        if (c.moveToFirst()) {
            // Loop through all results
            do {
                // Create a new todoList object and set the correct data
                ToDoItem item = new ToDoItem();
                item.setId(c.getInt(c.getColumnIndex("id")));
                item.setListId(c.getInt(c.getColumnIndex("list_id")));
                item.setTitle(c.getString(c.getColumnIndex("title")));

                boolean itemCompleted = (c.getInt(c.getColumnIndex("is_completed")) == 1);
                item.setCompleted(itemCompleted);

                toDoItems.add(item);
            } while (c.moveToNext()) ;
        }

        db.close();
        c.close();
        return toDoItems;
    }
}
