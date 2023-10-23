package com.example.choreplannerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.objects.Adult;
import com.example.choreplannerapp.objects.Child;

import java.util.ArrayList;

public class UserBaseAdapter extends BaseAdapter {

    private final Context context; //context
    private final ArrayList<Child> children; //data source of the list adapter

    public UserBaseAdapter(Context context, ArrayList<Child> children) {
        this.context = context;
        this.children = children;
    }

    @Override
    public int getCount() {
        return children.size();
    }

    @Override
    public Object getItem(int position) {
        return children.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserBaseAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_adult_user_grid_cell, parent, false);
            viewHolder = new UserBaseAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserBaseAdapter.ViewHolder) convertView.getTag();
        }

        Child currentUser = (Child) getItem(position);
        viewHolder.vhName.setText(currentUser.getName());
    //    viewHolder.vhImage.setImageResource(currentUser.getImage());
        viewHolder.vhLayout.setBackgroundColor(currentUser.getColor());

        return convertView;
    }

    private static class ViewHolder {
        final TextView vhName;
        final ImageView vhImage;
        LinearLayout vhLayout;

        public ViewHolder(View view) {
            vhName = view.findViewById(R.id.user_name_textview);
            vhImage = view.findViewById(R.id.user_name_image);
            vhLayout = view.findViewById(R.id.user_linear_layout);
        }
    }
}

