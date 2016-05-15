package com.example.josien.josien_pset4;

/*
*  Josien Jansen
*  11162295
*  Universiteit van Amsterdam
*/

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.josien.josien_pset4.Model.ToDoItem;

import java.util.ArrayList;

/*
* Handles everything the user could do with a todoitem
* Like add an item and store it in the database & listview
* Update everything that changes.
 */

public class SecondActivity extends AppCompatActivity {

    // Declare variables
    EditText editText_item;
    ListView toDoItemsList;
    ArrayAdapter<ToDoItem> listAdapter;
    int listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        editText_item = (EditText) findViewById(R.id.todo_edittext);
        toDoItemsList = (ListView) findViewById(R.id.list_view2);

        Intent intent = getIntent();
        listId = intent.getIntExtra("list_id", 500);

        setupTodoListView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("listid", listId);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listId = savedInstanceState.getInt("listid");
    }

    /*
    * Add a to-do into the database
    */
    public void addTodo(View view) {

        String input = String.valueOf(editText_item.getText());

        // Check for correct input.
        if (!input.matches("[a-zA-Z1-9\\s]+")) {
            Toast.makeText(this, this.getString(R.string.correct_input), Toast.LENGTH_SHORT).show();
        } else {
            ToDoManager toDoManager = ToDoManager.getInstance(this);
            ToDoItem toDoItem = new ToDoItem(editText_item.getText().toString());
            toDoItem.setListId(listId);
            // Make sure the item isn't marked as done
            toDoItem.setCompleted(false);

            toDoManager.saveToDoItem(toDoItem);
            // Add item to the ListView
            listAdapter.add(toDoItem);
            Toast.makeText(this, this.getString(R.string.item_added), Toast.LENGTH_SHORT).show();
            // Clear the input field
            editText_item.setText("");
        }
    }

    /*
    * Combine the Listview and database correctly
    * Also handles items done or deleted
    */
    public void setupTodoListView() {

        final ToDoManager toDoManager = ToDoManager.getInstance(this);

        final ArrayList<ToDoItem> todoItemArray = toDoManager.retrieveTodoItemsByListId(listId);

        // Make a new ArrayAdapter to handle the objects, and add them to the view
        listAdapter = new ToDoItemAdapter(this,
                android.R.layout.simple_list_item_1, todoItemArray);

        // Apply the adapter on the ViewList
        toDoItemsList.setAdapter(listAdapter);

        // Handles the items that are done
        toDoItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Clicked item value
                ToDoItem item = (ToDoItem) toDoItemsList.getItemAtPosition(position);
                TextView itemTextView = (TextView) view.findViewById(android.R.id.text1);

                if (item.isCompleted()) {
                    // Remove paint flags to remove strike_thru
                    itemTextView.setPaintFlags(0);
                    item.setCompleted(false);
                } else {
                    // Set paint flags through the item
                    itemTextView.setPaintFlags(itemTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Toast.makeText(getApplicationContext(), getString(R.string.todo_done), Toast.LENGTH_SHORT).show();
                    item.setCompleted(true);
                }

                toDoManager.saveToDoItem(item);
            }
        });

        // Handles long-click to remove an item
        toDoItemsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Clicked item value
                ToDoItem item = (ToDoItem) toDoItemsList.getItemAtPosition(position);
                // Delete item from database
                toDoManager.deleteItem(item.getId());
                // Delete item from listView
                listAdapter.remove(item);
                Toast.makeText(getApplicationContext(), getString(R.string.delete_list), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
