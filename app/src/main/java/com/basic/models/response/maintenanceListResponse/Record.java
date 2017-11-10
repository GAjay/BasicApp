package com.basic.models.response.maintenanceListResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model class to hold the maintenance list web service response.
 */

public class Record implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("RoomSpaceMaintenanceID")
    private String roomSpaceMaintenanceId;
    @SerializedName("title")
    private String title;
    @SerializedName("Cause")
    private String cause;
    @SerializedName("DateReported")
    private String dateReported;
    @SerializedName("CompleteDate")
    private String completeDate;
    @SerializedName("sub_category")
    private String subCategory;
    @SerializedName("main_category")
    private String mainCategory;
    @SerializedName("OccupantPresent")
    private boolean isOccupantPresent;
    @SerializedName("description")
    private String description;
    @SerializedName("comments")
    private String comments;

    @Override
    public String toString() {
        return "Record{" +
                "status='" + status + '\'' +
                ", roomSpaceMaintenanceId='" + roomSpaceMaintenanceId + '\'' +
                ", title='" + title + '\'' +
                ", cause='" + cause + '\'' +
                ", dateReported='" + dateReported + '\'' +
                ", completeDate='" + completeDate + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", mainCategory='" + mainCategory + '\'' +
                ", isOccupantPresent=" + isOccupantPresent +
                ", description='" + description + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.roomSpaceMaintenanceId);
        dest.writeString(this.title);
        dest.writeString(this.cause);
        dest.writeString(this.dateReported);
        dest.writeString(this.completeDate);
        dest.writeString(this.subCategory);
        dest.writeString(this.mainCategory);
        dest.writeByte(this.isOccupantPresent ? (byte) 1 : (byte) 0);
        dest.writeString(this.description);
        dest.writeString(this.comments);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.status = in.readString();
        this.roomSpaceMaintenanceId = in.readString();
        this.title = in.readString();
        this.cause = in.readString();
        this.dateReported = in.readString();
        this.completeDate = in.readString();
        this.subCategory = in.readString();
        this.mainCategory = in.readString();
        this.isOccupantPresent = in.readByte() != 0;
        this.description = in.readString();
        this.comments = in.readString();
    }

    public static final Creator<com.basic.models.response.maintenanceListResponse.Record> CREATOR = new Creator<com.basic.models.response.maintenanceListResponse.Record>() {
        @Override
        public com.basic.models.response.maintenanceListResponse.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.maintenanceListResponse.Record(source);
        }

        @Override
        public com.basic.models.response.maintenanceListResponse.Record[] newArray(int size) {
            return new com.basic.models.response.maintenanceListResponse.Record[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomSpaceMaintenanceId() {
        return roomSpaceMaintenanceId;
    }

    public String getTitle() {
        return title;
    }

    public String getCause() {
        return cause;
    }

    public String getDateReported() {
        return dateReported;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public boolean isOccupantPresent() {
        return isOccupantPresent;
    }

    public String getDescription() {
        return description;
    }

    public String getComments() {
        return comments;
    }
}
