package com.basic.models.response.newmaintenanceSubCatResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.basic.models.response.newMaintenanceCategoryResponse.Title;

/**
 * Model class to hold the maintenance list web service response.
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

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", content=" + content +
                ", title=" + title +
                ", updated='" + updated + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public Content getContent() {
        return content;
    }

    public Title getTitle() {
        return title;
    }

    public String getUpdated() {
        return updated;
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

    public static final Creator<com.basic.models.response.newmaintenanceSubCatResponse.Entry> CREATOR = new Creator<com.basic.models.response.newmaintenanceSubCatResponse.Entry>() {
        @Override
        public com.basic.models.response.newmaintenanceSubCatResponse.Entry createFromParcel(Parcel source) {
            return new com.basic.models.response.newmaintenanceSubCatResponse.Entry(source);
        }

        @Override
        public com.basic.models.response.newmaintenanceSubCatResponse.Entry[] newArray(int size) {
            return new com.basic.models.response.newmaintenanceSubCatResponse.Entry[size];
        }
    };
}
