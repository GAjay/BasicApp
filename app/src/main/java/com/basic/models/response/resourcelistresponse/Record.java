package com.basic.models.response.resourcelistresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Record implements Parcelable {

    @SerializedName("DateStart")
    private String mDateStart;
    @SerializedName("DateEnd")
    private String mDateEnd;
    @SerializedName("resource")
    private String mResource;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("resource_type")
    private String mResourceTypeEnum;
    @SerializedName("Comments")
    private String mComments;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("ResourceBookingStatusEnum")
    private int mResourceBookingStatusEnum;

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

    public String getmResource() {
        return mResource;
    }

    public void setmResource(String mResource) {
        this.mResource = mResource;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmResourceTypeEnum() {
        return mResourceTypeEnum;
    }

    public void setmResourceTypeEnum(String mResourceTypeEnum) {
        this.mResourceTypeEnum = mResourceTypeEnum;
    }

    public String getmComments() {
        return mComments;
    }

    public void setmComments(String mComments) {
        this.mComments = mComments;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmResourceBookingStatusEnum() {
        return mResourceBookingStatusEnum;
    }

    public void setmResourceBookingStatusEnum(int mResourceBookingStatusEnum) {
        this.mResourceBookingStatusEnum = mResourceBookingStatusEnum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDateStart);
        dest.writeString(this.mDateEnd);
        dest.writeString(this.mResource);
        dest.writeString(this.mStatus);
        dest.writeString(this.mResourceTypeEnum);
        dest.writeString(this.mComments);
        dest.writeString(this.mDescription);
        dest.writeInt(this.mResourceBookingStatusEnum);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.mDateStart = in.readString();
        this.mDateEnd = in.readString();
        this.mResource = in.readString();
        this.mStatus = in.readString();
        this.mResourceTypeEnum = in.readString();
        this.mComments = in.readString();
        this.mDescription = in.readString();
        this.mResourceBookingStatusEnum = in.readInt();
    }

    public static final Creator<com.basic.models.response.resourcelistresponse.Record> CREATOR = new Creator<com.basic.models.response.resourcelistresponse.Record>() {
        @Override
        public com.basic.models.response.resourcelistresponse.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.resourcelistresponse.Record(source);
        }

        @Override
        public com.basic.models.response.resourcelistresponse.Record[] newArray(int size) {
            return new com.basic.models.response.resourcelistresponse.Record[size];
        }
    };

    @Override
    public String toString() {
        return "Record{" +
                "mDateStart='" + mDateStart + '\'' +
                ", mDateEnd='" + mDateEnd + '\'' +
                ", mResource='" + mResource + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mResourceTypeEnum='" + mResourceTypeEnum + '\'' +
                ", mComments='" + mComments + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mResourceBookingStatusEnum=" + mResourceBookingStatusEnum +
                '}';
    }
}
