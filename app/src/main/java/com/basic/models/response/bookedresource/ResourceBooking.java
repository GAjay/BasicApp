package com.basic.models.response.bookedresource;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 23/9/16.
 */

public class ResourceBooking implements Parcelable {

/*    <ResourceBookingID>53</ResourceBookingID>
    <EntryID>3763</EntryID>
    <ResourceID>19</ResourceID>
    <DateStart>2016-09-28T05:17:00</DateStart>
    <DateEnd>2016-09-28T06:17:00</DateEnd>
    <Description></Description>
    <BookingID>0</BookingID>
    <AutoAssigned>false</AutoAssigned>
    <ResourceBookingStatusEnum>Assigned</ResourceBookingStatusEnum>
    <Comments></Comments>
    <DateModified>2016-09-28T06:19:00</DateModified>
    <DateCreated>2016-09-28T06:19:00</DateCreated>
    <SecurityUserID>82</SecurityUserID>
    <ProgramID>0</ProgramID>*/

    @SerializedName("ResourceBookingID")
    private String mResourceBookingID;
    @SerializedName("EntryID")
    private String mEntryID;
    @SerializedName("ResourceID")
    private String mResourceID;
    @SerializedName("DateStart")
    private String mDateStart;
    @SerializedName("DateEnd")
    private String mDateEnd;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("BookingID")
    private String mBookingID;
    @SerializedName("AutoAssigned")
    private String mAutoAssigned;
    @SerializedName("ResourceBookingStatusEnum")
    private String mResourceBookingStatusEnum;
    @SerializedName("Comments")
    private String mComments;
    @SerializedName("DateModified")
    private String mDateModified;
    @SerializedName("DateCreated")
    private String mDateCreated;
    @SerializedName("SecurityUserID")
    private String mSecurityUserID;
    @SerializedName("ProgramID")
    private String mProgramID;

    public String getmResourceBookingID() {
        return mResourceBookingID;
    }

    public void setmResourceBookingID(String mResourceBookingID) {
        this.mResourceBookingID = mResourceBookingID;
    }

    public String getmEntryID() {
        return mEntryID;
    }

    public void setmEntryID(String mEntryID) {
        this.mEntryID = mEntryID;
    }

    public String getmResourceID() {
        return mResourceID;
    }

    public void setmResourceID(String mResourceID) {
        this.mResourceID = mResourceID;
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

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmBookingID() {
        return mBookingID;
    }

    public void setmBookingID(String mBookingID) {
        this.mBookingID = mBookingID;
    }

    public String getmAutoAssigned() {
        return mAutoAssigned;
    }

    public void setmAutoAssigned(String mAutoAssigned) {
        this.mAutoAssigned = mAutoAssigned;
    }

    public String getmResourceBookingStatusEnum() {
        return mResourceBookingStatusEnum;
    }

    public void setmResourceBookingStatusEnum(String mResourceBookingStatusEnum) {
        this.mResourceBookingStatusEnum = mResourceBookingStatusEnum;
    }

    public String getmComments() {
        return mComments;
    }

    public void setmComments(String mComments) {
        this.mComments = mComments;
    }

    public String getmDateModified() {
        return mDateModified;
    }

    public void setmDateModified(String mDateModified) {
        this.mDateModified = mDateModified;
    }

    public String getmDateCreated() {
        return mDateCreated;
    }

    public void setmDateCreated(String mDateCreated) {
        this.mDateCreated = mDateCreated;
    }

    public String getmSecurityUserID() {
        return mSecurityUserID;
    }

    public void setmSecurityUserID(String mSecurityUserID) {
        this.mSecurityUserID = mSecurityUserID;
    }

    public String getmProgramID() {
        return mProgramID;
    }

    public void setmProgramID(String mProgramID) {
        this.mProgramID = mProgramID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mResourceBookingID);
        dest.writeString(this.mEntryID);
        dest.writeString(this.mResourceID);
        dest.writeString(this.mDateStart);
        dest.writeString(this.mDateEnd);
        dest.writeString(this.mDescription);
        dest.writeString(this.mBookingID);
        dest.writeString(this.mAutoAssigned);
        dest.writeString(this.mResourceBookingStatusEnum);
        dest.writeString(this.mComments);
        dest.writeString(this.mDateModified);
        dest.writeString(this.mDateCreated);
        dest.writeString(this.mSecurityUserID);
        dest.writeString(this.mProgramID);
    }

    public ResourceBooking() {
    }

    protected ResourceBooking(Parcel in) {
        this.mResourceBookingID = in.readString();
        this.mEntryID = in.readString();
        this.mResourceID = in.readString();
        this.mDateStart = in.readString();
        this.mDateEnd = in.readString();
        this.mDescription = in.readString();
        this.mBookingID = in.readString();
        this.mAutoAssigned = in.readString();
        this.mResourceBookingStatusEnum = in.readString();
        this.mComments = in.readString();
        this.mDateModified = in.readString();
        this.mDateCreated = in.readString();
        this.mSecurityUserID = in.readString();
        this.mProgramID = in.readString();
    }

    public static final Creator<com.basic.models.response.bookedresource.ResourceBooking> CREATOR = new Creator<com.basic.models.response.bookedresource.ResourceBooking>() {
        @Override
        public com.basic.models.response.bookedresource.ResourceBooking createFromParcel(Parcel source) {
            return new com.basic.models.response.bookedresource.ResourceBooking(source);
        }

        @Override
        public com.basic.models.response.bookedresource.ResourceBooking[] newArray(int size) {
            return new com.basic.models.response.bookedresource.ResourceBooking[size];
        }
    };
}
