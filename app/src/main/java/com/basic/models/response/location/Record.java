package com.basic.models.response.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 23/9/16.
 */

public class Record implements Parcelable {

    @SerializedName("Description")
    private String mDescription;
    @SerializedName("RoomLocationAreaID")
    private String mRoomLocationAreaID;
    @SerializedName("CategoryID")
    private String mCategoryID;
    @SerializedName("CountryID")
    private String mCountryID;
    @SerializedName("RoomLocationID")
    private String mRoomLocationID;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmRoomLocationAreaID() {
        return mRoomLocationAreaID;
    }

    public void setmRoomLocationAreaID(String mRoomLocationAreaID) {
        this.mRoomLocationAreaID = mRoomLocationAreaID;
    }

    public String getmCategoryID() {
        return mCategoryID;
    }

    public void setmCategoryID(String mCategoryID) {
        this.mCategoryID = mCategoryID;
    }

    public String getmCountryID() {
        return mCountryID;
    }

    public void setmCountryID(String mCountryID) {
        this.mCountryID = mCountryID;
    }

    public String getmRoomLocationID() {
        return mRoomLocationID;
    }

    public void setmRoomLocationID(String mRoomLocationID) {
        this.mRoomLocationID = mRoomLocationID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDescription);
        dest.writeString(this.mRoomLocationAreaID);
        dest.writeString(this.mCategoryID);
        dest.writeString(this.mCountryID);
        dest.writeString(this.mRoomLocationID);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.mDescription = in.readString();
        this.mRoomLocationAreaID = in.readString();
        this.mCategoryID = in.readString();
        this.mCountryID = in.readString();
        this.mRoomLocationID = in.readString();
    }

    public static final Creator<com.basic.models.response.location.Record> CREATOR = new Creator<com.basic.models.response.location.Record>() {
        @Override
        public com.basic.models.response.location.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.location.Record(source);
        }

        @Override
        public com.basic.models.response.location.Record[] newArray(int size) {
            return new com.basic.models.response.location.Record[size];
        }
    };
}
