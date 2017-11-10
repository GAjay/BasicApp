package com.basic.models.response.resourcetyperesponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ajay on 23/9/16.
 */

public class Record implements Parcelable {
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("ResourceTypeID")
    private String mResouceTypeID;
    @SerializedName("MinBookingHours")
    private String mMinBookingHours;
    @SerializedName("MaxBookingHours")
    private String mMaxBookingHours;
    @SerializedName("RoomLocationAreaID")
    private String mRoomLocationAreaID;


    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmResouceTypeID() {
        return mResouceTypeID;
    }

    public void setmResouceTypeID(String mResouceTypeID) {
        this.mResouceTypeID = mResouceTypeID;
    }

    public String getmMinBookingHours() {
        return mMinBookingHours;
    }

    public void setmMinBookingHours(String mMinBookingHours) {
        this.mMinBookingHours = mMinBookingHours;
    }

    public String getmMaxBookingHours() {
        return mMaxBookingHours;
    }

    public void setmMaxBookingHours(String mMaxBookingHours) {
        this.mMaxBookingHours = mMaxBookingHours;
    }

    public String getmRoomLocationAreaID() {
        return mRoomLocationAreaID;
    }

    public void setmRoomLocationID(String mRoomLocationID) {
        this.mRoomLocationAreaID = mRoomLocationID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDescription);
        dest.writeString(this.mResouceTypeID);
        dest.writeString(this.mMinBookingHours);
        dest.writeString(this.mMaxBookingHours);
        dest.writeString(this.mRoomLocationAreaID);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.mDescription = in.readString();
        this.mResouceTypeID = in.readString();
        this.mMinBookingHours = in.readString();
        this.mMaxBookingHours = in.readString();
        this.mRoomLocationAreaID = in.readString();
    }

    public static final Creator<com.basic.models.response.resourcetyperesponse.Record> CREATOR = new Creator<com.basic.models.response.resourcetyperesponse.Record>() {
        @Override
        public com.basic.models.response.resourcetyperesponse.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.resourcetyperesponse.Record(source);
        }

        @Override
        public com.basic.models.response.resourcetyperesponse.Record[] newArray(int size) {
            return new com.basic.models.response.resourcetyperesponse.Record[size];
        }
    };
}
