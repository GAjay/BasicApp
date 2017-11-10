package com.basic.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basic.R;
import com.basic.models.response.maintenanceListResponse.Entry;
import com.basic.utils.DateUtils;

import java.util.ArrayList;

/**
 * Adapter class to contain the item of Maintenance list items.
 */

public class MaintenanceListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<Entry> itemList;
    Context context;

    public MaintenanceListAdapter(Context context, ArrayList<Entry> itemList) {
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
        com.basic.adapters.MaintenanceListAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_maintenance_list, null);
            holder = new com.basic.adapters.MaintenanceListAdapter.ViewHolder();

            holder.maintenance_title_text_view = (TextView) convertView.findViewById(R.id.maintenance_title_text_view);
            holder.reported_date_text_view = (TextView) convertView.findViewById(R.id.reported_date_text_view);
            holder.maintenance_description_text_view = (TextView) convertView.findViewById(R.id.maintenance_description_text_view);
            holder.maintenance_status_text_view = (TextView) convertView.findViewById(R.id.maintenance_status_text_view);
            holder.maintenance_view = (View) convertView.findViewById(R.id.view_dotted_divider_title);
            holder.maintenace_bg =(LinearLayout) convertView.findViewById(R.id.maintenance_status_background);
            convertView.setTag(holder);
        } else {
            holder = (com.basic.adapters.MaintenanceListAdapter.ViewHolder) convertView.getTag();
        }
        if(TextUtils.isEmpty(itemList.get(position).getContent().getRecord().getSubCategory())){
            holder.maintenance_title_text_view.setText("No title available");
        }else {
            holder.maintenance_title_text_view.setText(itemList.get(position).getContent().getRecord().getSubCategory());
        }
        if(TextUtils.isEmpty(itemList.get(position).getContent().
                getRecord().getDateReported())){
            holder.reported_date_text_view.setText("NA");
        }else {
            holder.reported_date_text_view.setText(DateUtils.getDate(DateUtils.convertToDeviceTime(itemList.get(position).getContent().
                    getRecord().getDateReported().replace("T", " "))));
        }
        if(TextUtils.isEmpty(itemList.get(position).getContent().getRecord().getTitle())){
            holder.maintenance_description_text_view.setText("NA");
        }
        else {
            holder.maintenance_description_text_view.setText(itemList.get(position).getContent().getRecord().getTitle());
        }
        holder.maintenance_view.getBackground().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        holder.maintenance_status_text_view.setText(itemList.get(position).getContent().getRecord().getStatus());

        if (itemList.get(position).getContent().getRecord().getStatus().
                equalsIgnoreCase("Job Submitted")) {
            holder.maintenace_bg.setBackgroundResource(R.drawable
                    .drawable_bottom_corner_round_bg_green);
        } else if (itemList.get(position).getContent().getRecord().getStatus()
                .equalsIgnoreCase("Awaiting for Parts")
                || itemList.get(position).getContent().getRecord().getStatus()
                .equalsIgnoreCase("Awaiting for Contractor")) {
            holder.maintenace_bg.setBackgroundResource(R.drawable
                    .drawable_bottom_corner_round_bg_red);
        } else if (itemList.get(position).getContent().getRecord().getStatus()
                .equalsIgnoreCase("Job Scheduled")) {
            holder.maintenace_bg.setBackgroundResource(R.drawable
                    .drawable_bottom_corner_round_bg_blue);
        } else if (itemList.get(position).getContent().getRecord().getStatus()
                .equalsIgnoreCase("Job in Progress")) {
            holder.maintenace_bg.setBackgroundResource(R.drawable
                    .drawable_bottom_corner_round_bg_yellow);
        } else if (itemList.get(position).getContent().getRecord().getStatus().
                equalsIgnoreCase("Job Received")) {
            holder.maintenace_bg.setBackgroundResource(R.drawable.
                    drawable_bottom_corner_round_bg_skyblue);
        } else if (itemList.get(position).getContent().getRecord().getStatus().
                equalsIgnoreCase("Please contact office")) {
            holder.maintenace_bg.setBackgroundResource(R.drawable.
                    drawable_bottom_corner_round_bg_olive_green);
        } else if(itemList.get(position).getContent().getRecord().getStatus().
                equalsIgnoreCase("Job Completed")){
            holder.maintenace_bg.setBackgroundResource(R.drawable
                    .drawable_bottom_corner_round_bg_purple);
        }
        else if(itemList.get(position).getContent().getRecord().getStatus().
                equalsIgnoreCase("Closed")){
            holder.maintenace_bg.setBackgroundResource(R.drawable
                    .drawable_bottom_corner_round_bg_darkgray);
        }
        else {
            holder.maintenace_bg.setBackgroundResource(R.drawable.
                    drawable_bottom_corner_round_bg_gray_status);
        }


        return convertView;
    }

    private class ViewHolder {
        TextView maintenance_title_text_view;
        TextView reported_date_text_view;
        TextView maintenance_description_text_view;
        TextView maintenance_status_text_view;
        View maintenance_view;
        LinearLayout maintenace_bg;
    }
}