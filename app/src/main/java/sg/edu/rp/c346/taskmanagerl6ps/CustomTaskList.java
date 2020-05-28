package sg.edu.rp.c346.taskmanagerl6ps;

import android.content.ContentUris;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomTaskList extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Task> tasksList;


    public CustomTaskList(Context context, int resource, ArrayList<Task> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        tasksList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) parent_context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout_id, parent, false);
        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvNumber = rowView.findViewById(R.id.textViewNumba);
        TextView tvDesc = rowView.findViewById(R.id.textViewDescription);

        Task currentItem = tasksList.get(position);

        tvName.setText(currentItem.getName());
        tvDesc.setText(currentItem.getDesc());
        tvNumber.setText(currentItem.getId() + ":");
        return rowView;
    }

}
