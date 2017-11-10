package com.basic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.basic.R;
import com.basic.models.FilterModel;

import java.util.ArrayList;

/**
 *
 */
public class FilterAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<FilterModel> itemList;
    Context context;

    public FilterAdapter(Context context, ArrayList<FilterModel> itemList) {
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_filter_layout, null);
            holder = new ViewHolder();

            holder.filter_choice_radio_button = (ImageView) convertView.findViewById(R.id.filter_choice_radio_button);
            holder.filter_text = (TextView) convertView.findViewById(R.id.filter_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.filter_text.setText(itemList.get(position).getFilterName());
        if (itemList.get(position).isChecked()) {
            holder.filter_choice_radio_button.setImageResource(R.drawable.ic_collected_icon);
            holder.filter_text.setTextColor(context.getResources().getColor(R.color.sidebar_parcel_title_color));
        } else {
            holder.filter_choice_radio_button.setImageResource(R.drawable.ic_collection_icon);
            holder.filter_text.setTextColor(context.getResources().getColor(R.color.login_edit_text_hint_color));
        }

        if (position == 0) {
            convertView.setBackground(context.getResources().getDrawable(R.drawable.drawable_top_corner_round_bg_white));
        } else {
            if (position == itemList.size() - 1) {
                convertView.setBackground(context.getResources().getDrawable(R.drawable.drawable_bottom_corner_round_bg_white));
            } else {
                convertView.setBackground(context.getResources().getDrawable(R.drawable.parcel_filter_list_item_bg));
            }

        }
        return convertView;
    }

    private class ViewHolder {
        TextView filter_text;
        ImageView filter_choice_radio_button;
    }
}
