package com.basic.models.response.parcellistresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vikas on 15/9/16.
 */
public class ParcelListResponse {
    @SerializedName("entry")
    private ArrayList<Entry> entryArrayList;

    public ArrayList<Entry> getEntryArrayList() {
        return entryArrayList;
    }

    @Override
    public String toString() {
        return "ParcelListResponse{" +
                "entryArrayList=" + entryArrayList +
                '}';
    }
}
