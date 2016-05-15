package com.example.josien.josien_pset4;

/*
*  Josien Jansen
*  11162295
*  Universiteit van Amsterdam
*/


import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.josien.josien_pset4.Model.ToDoItem;

import java.util.ArrayList;

/*
* This Activity handles the Adapter of the to-do items
* It handles the view of items in the List and the 'done'-marker.
 */

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    Context context;
    int layoutResourceId;
    ArrayList<ToDoItem> data;

    public ToDoItemAdapter(Context context, int layoutResourceId, ArrayList<ToDoItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ToDoHolder holder;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ToDoHolder();
            holder.txtTitle = (TextView)row.findViewById(android.R.id.text1);

            row.setTag(holder);
        }
        else {
            holder = (ToDoHolder)row.getTag();
        }

        // Handles an item that is or isn't done
        ToDoItem item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        if (item.isCompleted()) {
            holder.txtTitle.setPaintFlags(holder.txtTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.txtTitle.setPaintFlags(0);
        }
        return row;
    }

    static class ToDoHolder {
        TextView txtTitle;
    }
}