package com.example.choreplannerapp.adapters;
// Sarah Meyer

// GPL - C202306

// File Name PostBaseAdapter.java

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.objects.Chore;
import com.example.choreplannerapp.objects.ProfileColor;

import java.util.ArrayList;

public class ColorBaseAdapter extends BaseAdapter {

    private final Context context; //context
    private final ArrayList<ProfileColor> colors; //data source of the list adapter

    public ColorBaseAdapter(Context context, ArrayList<ProfileColor> colors) {
        this.context = context;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.color_circle_cell, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProfileColor currentColor = (ProfileColor) getItem(position);
        viewHolder.vhName.setText(currentColor.getName());
        viewHolder.vhName.setBackgroundColor(currentColor.getColor());

        return convertView;
    }

    private static class ViewHolder {

        final TextView vhName;

        public ViewHolder(View view) {

            vhName = view.findViewById(R.id.color_circle);
        }
    }
}