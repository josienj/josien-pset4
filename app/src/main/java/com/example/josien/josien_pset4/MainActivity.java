package com.example.josien.josien_pset4;

/* Josien Jansen
*  11162295
*  Universiteit van Amsterdam
*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.josien.josien_pset4.Model.ToDoList;

import java.util.ArrayList;

/*
* Handles everything the user could do with the app
* Like add a to-do and store it in the database & listview
* Update everything that changes
 */

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ListView toDoListsList;
    ArrayAdapter<ToDoList> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.todo_edittext);
        toDoListsList = (ListView) findViewById(R.id.list_view);

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
        } else {
            ToDoManager toDoManager = ToDoManager.getInstance(this);
            ToDoList toDoList = new ToDoList(editText.getText().toString());

            toDoManager.saveToDoList(toDoList);
            // Add item to the ListView
            listAdapter.add(toDoList);
            Toast.makeText(this, "List is added!", Toast.LENGTH_SHORT).show();
            // Clear the input field
            editText.setText("");
        }
    }

    /*
    * Combine the listview and database correctly
    */
    public void setupTodoListView() {

        final ToDoManager toDoManager = ToDoManager.getInstance(this);

        final ArrayList<ToDoList> todoListArray = toDoManager.retrieveTodoLists();

        // Make a new ArrayAdapter to handle the objects, and add them to the view
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, todoListArray);

        // Apply the adapter on the ViewList
        toDoListsList.setAdapter(listAdapter);
        final Intent intent = new Intent(this, SecondActivity.class);

        toDoListsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Clicked item value
                ToDoList list = (ToDoList) toDoListsList.getItemAtPosition(position);

                intent.putExtra("list_id", list.getId());
                startActivity(intent);
            }
        });


        // Add a new on click listener
        toDoListsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Clicked item value
                ToDoList list = (ToDoList) toDoListsList.getItemAtPosition(position);
                // Delete item from database
                toDoManager.deleteList(list.getId());
                // Delete item from listView
                listAdapter.remove(list);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}