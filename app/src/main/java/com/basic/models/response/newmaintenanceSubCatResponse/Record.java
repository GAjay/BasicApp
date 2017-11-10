package com.basic.models.response.newmaintenanceSubCatResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model class to hold the maintenance list web service response.
 */

public class Record implements Parcelable {
    @SerializedName("RoomSpaceMaintenanceCategoryID")
    private String roomSpaceMaintenanceCategoryId;
    @SerializedName("RoomSpaceMaintenanceItemID")
    private String roomSpaceMaintenanceItemId;
    @SerializedName("Comments")
    private String comments;
    @SerializedName("Description")
    private String description;


    public String getRoomSpaceMaintenanceCategoryId() {
        return roomSpaceMaintenanceCategoryId;
    }

    public String getRoomSpaceMaintenanceItemId() {
        return roomSpaceMaintenanceItemId;
    }

    public String getComments() {
        return comments;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.roomSpaceMaintenanceCategoryId);
        dest.writeString(this.roomSpaceMaintenanceItemId);
        dest.writeString(this.comments);
        dest.writeString(this.description);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.roomSpaceMaintenanceCategoryId = in.readString();
        this.roomSpaceMaintenanceItemId = in.readString();
        this.comments = in.readString();
        this.description = in.readString();
    }

    public static final Creator<com.basic.models.response.newmaintenanceSubCatResponse.Record> CREATOR = new Creator<com.basic.models.response.newmaintenanceSubCatResponse.Record>() {
        @Override
        public com.basic.models.response.newmaintenanceSubCatResponse.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.newmaintenanceSubCatResponse.Record(source);
        }

        @Override
        public com.basic.models.response.newmaintenanceSubCatResponse.Record[] newArray(int size) {
            return new com.basic.models.response.newmaintenanceSubCatResponse.Record[size];
        }
    };
}
