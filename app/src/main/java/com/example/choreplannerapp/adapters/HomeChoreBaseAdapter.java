package com.example.choreplannerapp.adapters;

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

public class HomeChoreBaseAdapter extends BaseAdapter {
    private final Context context; //context
    private final ArrayList<Chore> chores; //data source of the list adapter

    public HomeChoreBaseAdapter(Context context, ArrayList<Chore> chores) {
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
        HomeChoreBaseAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_chore_grid_layout, parent, false);
            viewHolder = new HomeChoreBaseAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HomeChoreBaseAdapter.ViewHolder) convertView.getTag();
        }

        Chore currentChore = (Chore) getItem(position);
        viewHolder.vhChore.setText(currentChore.getChoreDescription());
        viewHolder.vhImage.setImageResource(currentChore.getChoreImage());

        return convertView;
    }

    private static class ViewHolder {
        final ImageView vhImage;
        final TextView vhChore;

        public ViewHolder(View view) {
            vhImage = view.findViewById(R.id.home_chore_grid_image);
            vhChore = view.findViewById(R.id.home_chore_grid_description);}
    }
}
