package com.basic.models.response.bookresourceid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * A class for "Entry" object from book resource id.
 */

public class Entry implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private com.basic.models.response.bookresourceid.Content content;

    @SerializedName("title")
    private Title title;

    @SerializedName("updated")
    private String updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public com.basic.models.response.bookresourceid.Content getContent() {
        return content;
    }

    public void setContent(com.basic.models.response.bookresourceid.Content content) {
        this.content = content;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", content=" + content +
                ", title=" + title +
                ", updated='" + updated + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.content, flags);
        dest.writeParcelable(this.title, flags);
        dest.writeString(this.updated);
    }

    public Entry() {
    }

    protected Entry(Parcel in) {
        this.id = in.readString();
        this.content = in.readParcelable(Content.class.getClassLoader());
        this.title = in.readParcelable(Title.class.getClassLoader());
        this.updated = in.readString();
    }

    public static final Creator<com.basic.models.response.bookresourceid.Entry> CREATOR = new Creator<com.basic.models.response.bookresourceid.Entry>() {
        @Override
        public com.basic.models.response.bookresourceid.Entry createFromParcel(Parcel source) {
            return new com.basic.models.response.bookresourceid.Entry(source);
        }

        @Override
        public com.basic.models.response.bookresourceid.Entry[] newArray(int size) {
            return new com.basic.models.response.bookresourceid.Entry[size];
        }
    };
}
