package com.basic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.basic.models.response.resourcelistresponse.Entry;

import java.util.ArrayList;

/**
 * Created by ajay on 23/9/16.
 */

public class ResourceTypeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<Entry> itemList;
    Context context;

    public ResourceTypeAdapter(Context context, ArrayList<Entry> itemList) {
        this.itemList = itemList;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
