package com.basic.models.response.newMaintenanceCategoryResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model class to hold the maintenance list web service response.
 */

public class Record implements Parcelable {
    @SerializedName("Comments")
    private String comments;
    @SerializedName("RoomSpaceMaintenanceCategoryID")
    private String roomSpaceMaintenanceCategoryId;
    @SerializedName("Description")
    private String description;


    public String getComments() {
        return comments;
    }

    public String getRoomSpaceMaintenanceCategoryId() {
        return roomSpaceMaintenanceCategoryId;
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
        dest.writeString(this.comments);
        dest.writeString(this.roomSpaceMaintenanceCategoryId);
        dest.writeString(this.description);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.comments = in.readString();
        this.roomSpaceMaintenanceCategoryId = in.readString();
        this.description = in.readString();
    }

    public static final Creator<com.basic.models.response.newMaintenanceCategoryResponse.Record> CREATOR = new Creator<com.basic.models.response.newMaintenanceCategoryResponse.Record>() {
        @Override
        public com.basic.models.response.newMaintenanceCategoryResponse.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.newMaintenanceCategoryResponse.Record(source);
        }

        @Override
        public com.basic.models.response.newMaintenanceCategoryResponse.Record[] newArray(int size) {
            return new com.basic.models.response.newMaintenanceCategoryResponse.Record[size];
        }
    };
}
