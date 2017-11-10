package com.basic.models.response.resourceavailabilityresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ajay on 23/9/16.
 */


public class Record implements Parcelable {
    @SerializedName("ResourceBookingID")
    private String mDescription;
    @SerializedName("ResourceID")
    private String mResouceTypeID;
    @SerializedName("ResourceBookingStatusEnum")
    private String mMinBookingHours;
    @SerializedName("Description")
    private String mMaxBookingHours;
    @SerializedName("DateStart")
    private String mDateStart;
    @SerializedName("DateEnd")
    private String mDateEnd;
    @SerializedName("Resource_Description")
    private String mResourcedescription;


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

    public String getmDateStart() {
        return mDateStart;
    }

    public void setmDateStart(String mDateStart) {
        this.mDateStart = mDateStart;
    }

    public String getmDateEnd() {
        return mDateEnd;
    }

    public void setmDateEnd(String mDateEnd) {
        this.mDateEnd = mDateEnd;
    }

    public String getmResourcedescription() {
        return mResourcedescription;
    }

    public void setmResourcedescription(String mResourcedescription) {
        this.mResourcedescription = mResourcedescription;
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
        dest.writeString(this.mDateStart);
        dest.writeString(this.mDateEnd);
        dest.writeString(this.mResourcedescription);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.mDescription = in.readString();
        this.mResouceTypeID = in.readString();
        this.mMinBookingHours = in.readString();
        this.mMaxBookingHours = in.readString();
        this.mDateStart = in.readString();
        this.mDateEnd = in.readString();
        this.mResourcedescription = in.readString();
    }

    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel source) {
            return new Record(source);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}
