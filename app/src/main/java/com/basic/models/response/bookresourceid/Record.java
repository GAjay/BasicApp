package com.basic.models.response.bookresourceid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * A class for "Record" object from book resource id.
 */

public class Record implements Parcelable {



    @SerializedName("ResourceBookingID")
    private String mResourceBookingID;

    @SerializedName("ResourceID")
    private String mResourceID;

    public String getmResourceBookingID() {
        return mResourceBookingID;
    }

    public void setmResourceBookingID(String mResourceBookingID) {
        this.mResourceBookingID = mResourceBookingID;
    }

    public String getmResourceID() {
        return mResourceID;
    }

    public void setmResourceID(String mResourceID) {
        this.mResourceID = mResourceID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mResourceBookingID);
        dest.writeString(this.mResourceID);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.mResourceBookingID = in.readString();
        this.mResourceID = in.readString();
    }

    public static final Creator<com.basic.models.response.bookresourceid.Record> CREATOR = new Creator<com.basic.models.response.bookresourceid.Record>() {
        @Override
        public com.basic.models.response.bookresourceid.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.bookresourceid.Record(source);
        }

        @Override
        public com.basic.models.response.bookresourceid.Record[] newArray(int size) {
            return new com.basic.models.response.bookresourceid.Record[size];
        }
    };
}
