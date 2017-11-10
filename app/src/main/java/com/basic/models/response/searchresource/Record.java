package com.basic.models.response.searchresource;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 23/9/16.
 */

public class Record implements Parcelable {

    @SerializedName("ResourceID")
    private String mResourceID;

    @SerializedName("Description")
    private String mDescription;

    @SerializedName("ResourceTypeID")
    private String mResourceTypeID;

    public String getmResourceID() {
        return mResourceID;
    }

    public void setmResourceID(String mResourceID) {
        this.mResourceID = mResourceID;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmResourceTypeID() {
        return mResourceTypeID;
    }

    public void setmResourceTypeID(String mResourceTypeID) {
        this.mResourceTypeID = mResourceTypeID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mResourceID);
        dest.writeString(this.mDescription);
        dest.writeString(this.mResourceTypeID);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.mResourceID = in.readString();
        this.mDescription = in.readString();
        this.mResourceTypeID = in.readString();
    }

    public static final Creator<com.basic.models.response.searchresource.Record> CREATOR = new Creator<com.basic.models.response.searchresource.Record>() {
        @Override
        public com.basic.models.response.searchresource.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.searchresource.Record(source);
        }

        @Override
        public com.basic.models.response.searchresource.Record[] newArray(int size) {
            return new com.basic.models.response.searchresource.Record[size];
        }
    };
}
