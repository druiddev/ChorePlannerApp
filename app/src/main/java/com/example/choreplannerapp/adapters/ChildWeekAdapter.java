package com.example.choreplannerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public class ChildWeekAdapter extends BaseAdapter {

    private final Context context; //context
    private final ArrayList<String> days; //data source of the list adapter

    public ChildWeekAdapter(Context context, ArrayList<String> days) {
        this.context = context;
        this.days = days;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChildWeekAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chore_list_layout, parent, false);
            viewHolder = new ChildWeekAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildWeekAdapter.ViewHolder) convertView.getTag();
        }

        String currentDay = (String) getItem(position);
        viewHolder.vhChore.setText(currentDay);

        return convertView;
    }

    private static class ViewHolder {
        final TextView vhChore;

        public ViewHolder(View view) {
            vhChore = view.findViewById(R.id.chore_list_description);
        }
    }
}

