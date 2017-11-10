package com.basic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basic.R;
import com.basic.models.NavigationItemModel;

import java.util.ArrayList;

/**
 * Side bar adapter to hold the items of sidebar.
 */
public class NavigationAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<NavigationItemModel> itemList;
    Context context;

    public NavigationAdapter(Context context, ArrayList<NavigationItemModel> itemList) {
        this.itemList=itemList;
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
        ViewHolder holder;

        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_navigation_drawer, null);
            holder = new ViewHolder();

            holder.itemNameTextView = (TextView)convertView.findViewById(R.id.drawer_itemName);
            holder.itemLayout = (LinearLayout) convertView.findViewById(R.id.itemLayout);
            holder.drawer_icon = (ImageView)convertView.findViewById(R.id.drawer_icon);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        if(itemList.get(position).isChecked()){
            holder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.sidebar_item_selected_bg));
            convertView.setBackgroundColor(context.getResources().getColor(itemList.get(position).getItemImageSelected()));
            holder.itemNameTextView.setTextColor(context.getResources().getColor(itemList.get(position).getItemImageSelected()));
            holder.drawer_icon.setImageDrawable(context.getResources().getDrawable(itemList.get(position).getItemImage()));
        }else{
            holder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.list_item_bg_normal));
            convertView.setBackground(context.getResources().getDrawable(R.drawable.list_item_bg_normal));
            holder.itemNameTextView.setTextColor(context.getResources().getColor(itemList.get(position).getItemImageSelected()));
            holder.drawer_icon.setImageDrawable(context.getResources().getDrawable(itemList.get(position).getItemImage()));
        }

        holder.itemNameTextView.setText((itemList.get(position).getItemName()));

        return convertView;
    }

    private class ViewHolder{
        TextView itemNameTextView;
        ImageView drawer_icon;
        LinearLayout itemLayout;
    }
}
