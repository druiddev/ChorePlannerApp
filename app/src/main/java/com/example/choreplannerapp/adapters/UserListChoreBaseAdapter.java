package com.example.choreplannerapp.adapters;
// Sarah Meyer

// GPL - C202306

// File Name PostBaseAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public class UserListChoreBaseAdapter extends BaseAdapter {

    private final Context context; //context
    private final ArrayList<Chore> chores; //data source of the list adapter

    public UserListChoreBaseAdapter(Context context, ArrayList<Chore> chores) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.user_chore_list_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Chore currentChore = (Chore) getItem(position);
        viewHolder.vhChoreImage.setImageResource(currentChore.getChoreImage());
        viewHolder.vhChore.setText(currentChore.getChoreDescription());
        viewHolder.vhPointValue.setText(String.valueOf(currentChore.getChorePointValue()));

        return convertView;
    }

    private static class ViewHolder {
        final ImageView vhChoreImage;
        final TextView vhChore;
        final TextView vhPointValue;

        public ViewHolder(View view) {
            vhChoreImage = view.findViewById(R.id.user_chore_list_image);
            vhChore = view.findViewById(R.id.user_chore_list_description);
            vhPointValue = view.findViewById(R.id.user_chore_list_point_value);
        }
    }
}