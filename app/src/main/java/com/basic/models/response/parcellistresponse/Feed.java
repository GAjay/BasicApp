package com.basic.models.response.parcellistresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vikas on 15/9/16.
 */
public class Feed {
    @SerializedName("xmlns")
    private String nameSpace;
    @SerializedName("title")
    private Title title;
    @SerializedName("id")
    private String id;
    @SerializedName("updated")
    private String updated;
    @SerializedName("link")
    private Link link;
    @SerializedName("entry")
    private ArrayList<Entry> entryArrayList;


    public String getNameSpace() {
        return nameSpace;
    }

    public Title getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getUpdated() {
        return updated;
    }

    public Link getLink() {
        return link;
    }

    public ArrayList<Entry> getEntryArrayList() {
        return entryArrayList;
    }
}
