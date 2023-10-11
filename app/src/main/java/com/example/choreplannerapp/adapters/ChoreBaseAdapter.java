package com.example.choreplannerapp.adapters;
// Sarah Meyer

// GPL - C202306

// File Name PostBaseAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public class ChoreBaseAdapter extends BaseAdapter {

    private final Context context; //context
    private final ArrayList<Chore> chores; //data source of the list adapter

    public ChoreBaseAdapter(Context context, ArrayList<Chore> chores) {
        this.context = context;
        this.chores = chores;
    }

    @Override
    public int getCount() {
        return chores.size();
    }

    @Override
    public Object getItem(int position) {
        return chores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chore_list_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Chore currentChore = (Chore) getItem(position);
        viewHolder.vhChore.setText(currentChore.getChoreDescription());

        return convertView;
    }

    private static class ViewHolder {
        final TextView vhChore;
        public ViewHolder(View view) {
            vhChore = view.findViewById(R.id.chore_list_description);
        }
    }
}