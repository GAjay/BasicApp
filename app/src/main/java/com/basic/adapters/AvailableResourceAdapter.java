package com.basic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.basic.R;
import com.basic.models.response.searchresource.Record;

import java.util.ArrayList;

/**
 * Created by ajay on 27/9/16.
 */

public class AvailableResourceAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<Record> itemList;
    Context context;

    public AvailableResourceAdapter(Context context, ArrayList<Record> itemList) {
        this.itemList = itemList;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.basic.adapters.AvailableResourceAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.available_list_item, null);
            holder = new com.basic.adapters.AvailableResourceAdapter.ViewHolder();


            holder.searchItemOfList = (TextView) convertView.findViewById(R.id.available_item);
            convertView.setTag(holder);
        } else {
            holder = (com.basic.adapters.AvailableResourceAdapter.ViewHolder) convertView.getTag();
        }

        holder.searchItemOfList.setText(itemList.get(position).getmDescription());


        return convertView;
    }

    private class ViewHolder {
        TextView searchItemOfList;
    }
}
