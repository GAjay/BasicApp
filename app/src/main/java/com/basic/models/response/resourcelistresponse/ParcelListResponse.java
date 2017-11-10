package com.basic.models.response.resourcelistresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ParcelListResponse {
    @SerializedName("entry")
    private ArrayList<com.basic.models.response.resourcelistresponse.Entry> entryArrayList;

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
