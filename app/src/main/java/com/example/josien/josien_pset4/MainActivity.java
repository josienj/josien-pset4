package com.example.josien.josien_pset4;

/* Josien Jansen
*  11162295
*  Universiteit van Amsterdam
*/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Application;

import java.util.ArrayList;

/*
* Handles everything the user could do with the app
* Like add a to-do and store it in the database & listview
* Update everything that changes
 */

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ListView newList;
    DBhelper DBhelper;
    ArrayAdapter<TodoList> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.todo_edittext);
        newList = (ListView) findViewById(R.id.list_view);
        DBhelper = new DBhelper(this, null, null, 1);
        initSingletons();

        // Get the application instance
        app = (MyApplication)getApplication();

        // Call a custom application method
        app.customAppMethod();

        // Call a custom method in MySingleton
        MySingleton.getInstance().customSingletonMethod();

        // Read the value of a variable in MySingleton
        String singletonVar = MySingleton.getInstance().customVar;
        setupTodoListView();
    }

    /*
    * Add a to-do into the database
    */
    public void addTodo(View view) {

        String input = String.valueOf(editText.getText());

        // Check for correct input.
        if (!input.matches("[a-zA-Z1-9\\s]+")) {
            Toast.makeText(this, "Input isn't correct, try again", Toast.LENGTH_SHORT).show();
        }
        else {

            TodoList todo = new TodoList(editText.getText().toString());

            DBhelper.addTodo(todo);
            // Add item to the ListView
            listAdapter.add(todo);
            Toast.makeText(this, "To-do is added!", Toast.LENGTH_SHORT).show();
            // Clear the input field
            editText.setText("");
        }
    }

    /*
    * Combine the listview and database correctly
    */
    public void setupTodoListView(){

        final ArrayList<TodoList> todoListArray = DBhelper.retrieveTodoLists();

        // Make a new ArrayAdapter to handle the objects, and add them to the view
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, todoListArray);

        // Apply the adapter on the ViewList
        newList.setAdapter(listAdapter);

        // Add a new on click listener
        newList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Clicked item value
                TodoList item = (TodoList) newList.getItemAtPosition(position);
                // Delete item from database
                DBhelper.deleteItem(item.get_id());
                // Delete item from listView
                listAdapter.remove(item);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
        MySingleton.initInstance();
    }

    public void customAppMethod()
    {
        // Custom application method
    }
}