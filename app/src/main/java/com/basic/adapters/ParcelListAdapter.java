package com.basic.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basic.R;
import com.basic.models.response.parcellistresponse.Entry;
import com.basic.utils.AppConstants;
import com.basic.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by vikas on 14/9/16.
 */
public class ParcelListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<Entry> itemList;
    Context context;

    public ParcelListAdapter(Context context, ArrayList<Entry> itemList) {
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
            convertView = mInflater.inflate(R.layout.item_parcel_list, null);
            holder = new ViewHolder();

            holder.parcel_title_text_view = (TextView) convertView.findViewById(R.id.parcel_title_text_view);
            holder.parcel_type_value_text_view = (TextView) convertView.findViewById(R.id.parcel_type_value_text_view);
            holder.receipt_date_value_text_view = (TextView) convertView.findViewById(R.id.receipt_date_value_text_view);
            holder.shipping_type_value_text_view = (TextView) convertView.findViewById(R.id.shipping_type_value_text_view);
            holder.issue_date_value_text_view = (TextView) convertView.findViewById(R.id.issue_date_value_text_view);
            holder.parcel_status_text_view = (TextView) convertView.findViewById(R.id.parcel_status_text_view);
            holder.parcel_title_divider =(View) convertView.findViewById(R.id.view_dotted_parcel_divider_title);
            holder.parcel_bg =(LinearLayout)convertView.findViewById(R.id.parcel_status_background);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(TextUtils.isEmpty(itemList.get(position).getContent().getRecord().getDescription())){
            holder.parcel_title_text_view.setText("No title available");

        } else{
            holder.parcel_title_text_view.setText(
                    itemList.get(position).getContent().getRecord().getDescription());

        }

        holder.parcel_title_divider.getBackground().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        holder.parcel_type_value_text_view.setText(itemList.get(position).getContent().getRecord().
                getParcelTypeValue());

        if(null!=itemList.get(position).getContent().getRecord().getReceiptDate() &&
                !TextUtils.isEmpty(itemList.get(position).getContent().getRecord().getReceiptDate())){
            holder.receipt_date_value_text_view.setText(DateUtils.getDate(DateUtils.convertToDeviceTime(
                    itemList.get(position).getContent().getRecord().getReceiptDate().replace("T"," "))));
        }else{
            holder.receipt_date_value_text_view.setText("NA");
        }

        /*holder.receipt_date_value_text_view.setText(DateUtils.getDate(itemList.get(position).getContent().
                getRecord().getReceiptDate().replace("T", " ")));*/

        holder.shipping_type_value_text_view.setText(itemList.get(position).getContent().getRecord().getShippingTypeValue());
        try {
            if(TextUtils.isEmpty(itemList.get(position).getContent().getRecord().getIssueDate())){
                holder.issue_date_value_text_view.setText("NA");
            }else{
                holder.issue_date_value_text_view.setText(DateUtils.getDate(DateUtils.convertToDeviceTime(
                        itemList.get(position).getContent().getRecord().getIssueDate().replace("T"," "))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int status = itemList.get(position).getContent().getRecord().getParcelStatusEnum();
        switch (status) {
            case AppConstants.PARCEL_STAUS_RECEIVED:
                holder.parcel_status_text_view.setText("New Parcel");
                holder.parcel_bg.setBackground(ContextCompat.getDrawable(context,R.drawable.drawable_bottom_corner_round_bg_green));
                break;
            case AppConstants.PARCEL_STATUS_RETURNED:
                holder.parcel_status_text_view.setText(itemList.get(position).getContent().getRecord().getStatusDescription());
                holder.parcel_bg.setBackground(ContextCompat.getDrawable(context,R.drawable.drawable_bottom_corner_round_bg_red));
                break;
            case AppConstants.PARCEL_STATUS_FORWARD:
                holder.parcel_status_text_view.setText(itemList.get(position).getContent().getRecord().getStatusDescription());
                holder.parcel_bg.setBackground(ContextCompat.getDrawable(context,R.drawable.drawable_bottom_corner_round_bg_yellow));
                break;
            case AppConstants.PARCEL_STATUS_ISSUED:
                holder.parcel_status_text_view.setText("Collected");
                holder.parcel_bg.setBackground(ContextCompat.getDrawable(context,R.drawable.drawable_bottom_corner_round_bg_oragne));
                break;
            default:
                holder.parcel_status_text_view.setText(itemList.get(position).getContent().getRecord().getStatusDescription());
                holder.parcel_bg.setBackground(ContextCompat.getDrawable(context,R.drawable.drawable_bottom_corner_round_bg_oragne));
                break;
        }
        return convertView;
    }

    private class ViewHolder {
        TextView parcel_title_text_view;
        TextView parcel_type_value_text_view;
        TextView receipt_date_value_text_view;
        TextView shipping_type_value_text_view;
        TextView issue_date_value_text_view;
        TextView parcel_status_text_view;
        View parcel_title_divider;
        LinearLayout parcel_bg;
    }
}
