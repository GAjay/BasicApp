package com.basic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.basic.R;
import com.basic.models.response.resourceavailabilityresponse.Record;
import com.basic.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by ajay on 27/9/16.
 */

public class AvailabilityResourceDetailAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<Record> itemList;
    Context context;

    public AvailabilityResourceDetailAdapter(Context context, ArrayList<Record> itemList) {
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
        AvailabilityResourceDetailAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.partial_resource_detail, null);
            holder = new AvailabilityResourceDetailAdapter.ViewHolder();
            holder.dateFrom = (TextView) convertView.findViewById(R.id.resource_dateFrom_item);
            holder.dateTo= (TextView) convertView.findViewById(R.id.resource_item_detail_dateto_text_view);
            convertView.setTag(holder);
        } else {
            holder = (AvailabilityResourceDetailAdapter.ViewHolder) convertView.getTag();
        }
        holder.dateFrom.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime
                (itemList.get(position).getmDateStart().replace("T", " "))));
        holder.dateTo.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime
                (itemList.get(position).getmDateEnd().replace("T", " "))));

        return convertView;
    }

    private class ViewHolder {
        TextView dateFrom;
        TextView dateTo;
    }
}
