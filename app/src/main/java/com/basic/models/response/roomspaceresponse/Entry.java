package com.basic.models.response.roomspaceresponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 15/9/16.
 */
public class Entry {
    @SerializedName("title")
    private com.basic.models.response.roomspaceresponse.Title mTitle;

    @SerializedName("id")
    private String mId;

    @SerializedName("updated")
    private String mUpdated;

    @SerializedName("content")
    private Content mContent;


    public com.basic.models.response.roomspaceresponse.Title getTitle() {
        return mTitle;
    }

    public void setTitle(Title title) {
        this.mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        this.mUpdated = updated;
    }

    public Content getContent() {
        return mContent;
    }

    public void setContent(Content content) {
        this.mContent = content;
    }



    @Override
    public String toString() {
        return "Entry{" +
                "title='" + mTitle + '\'' +
                ", id='" + mId + '\'' +
                ", updated='" + mUpdated + '\'' +
                ", content=" + mContent +
                '}';
    }
}
