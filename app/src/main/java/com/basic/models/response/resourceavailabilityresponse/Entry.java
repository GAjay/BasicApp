package com.basic.models.response.resourceavailabilityresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 23/9/16.
 */

public class Entry implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private Content content;

    @SerializedName("title")
    private Title title;

    @SerializedName("updated")
    private String updated;

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public static Creator<Entry> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("published")
    private String published;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
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

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel source) {
            return new Entry(source);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}
