package com.basic.models.response.maintaincesliderids;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model class to hold the maintenance list web service response.
 */

public class Record implements Parcelable {


    @SerializedName("RecordAttachmentID")
    private String mRecordAttachmentID;

    public String getmRecordAttachmentID() {
        return mRecordAttachmentID;
    }

    public void setmRecordAttachmentID(String mRecordAttachmentID) {
        this.mRecordAttachmentID = mRecordAttachmentID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mRecordAttachmentID);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.mRecordAttachmentID = in.readString();
    }

    public static final Creator<com.basic.models.response.maintaincesliderids.Record> CREATOR = new Creator<com.basic.models.response.maintaincesliderids.Record>() {
        @Override
        public com.basic.models.response.maintaincesliderids.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.maintaincesliderids.Record(source);
        }

        @Override
        public com.basic.models.response.maintaincesliderids.Record[] newArray(int size) {
            return new com.basic.models.response.maintaincesliderids.Record[size];
        }
    };
}
