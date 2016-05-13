package com.example.josien.josien_pset4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.josien.josien_pset4.Model.ToDoItem;
import com.example.josien.josien_pset4.Model.ToDoList;

import java.util.ArrayList;

/**
 * Created by Josien on 13-5-2016.
 */
public class SecondActivity extends AppCompatActivity {

    EditText editText;
    ListView toDoItemsList;
    ArrayAdapter<ToDoItem> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        editText = (EditText) findViewById(R.id.todo_edittext);
        toDoItemsList = (ListView) findViewById(R.id.list_view2);

        setupTodoListView();
    }

    /*
    * Add a to-do into the database
    */
    public void addList(View view) {

        String input = String.valueOf(editText.getText());

        // Check for correct input.
        if (!input.matches("[a-zA-Z1-9\\s]+")) {
            Toast.makeText(this, "Input isn't correct, try again", Toast.LENGTH_SHORT).show();
        }
        else {
            ToDoManager toDoManager = ToDoManager.getInstance(this);
            ToDoItem toDoItem = new ToDoItem(editText.getText().toString());

            toDoManager.saveToDoItem(toDoItem);
            // Add item to the ListView
            listAdapter.add(toDoItem);
            Toast.makeText(this, "List is added!", Toast.LENGTH_SHORT).show();
            // Clear the input field
            editText.setText("");
        }
    }

    /*
    * Combine the listview and database correctly
    */
    public void setupTodoListView(){

        final ToDoManager toDoManager = ToDoManager.getInstance(this);

        final ArrayList<ToDoList> todoListArray = toDoManager.retrieveTodoLists();

        // Make a new ArrayAdapter to handle the objects, and add them to the view
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, todoItemArray);

        // Apply the adapter on the ViewList
        toDoItemsList.setAdapter(listAdapter);

        // Add a new on click listener
        toDoItemsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Clicked item value
                ToDoList list = (ToDoList) toDoItemsList.getItemAtPosition(position);
                // Delete item from database
                toDoManager.deleteList(list.getId());
                // Delete item from listView
                listAdapter.remove(list);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
