package com.basic.models.response.newmaintenanceSubCatResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model class to hold the maintenance list web service response.
 */
public class Content implements Parcelable {

    @SerializedName("Record")
    private com.basic.models.response.newmaintenanceSubCatResponse.Record Record;

    @SerializedName("type")
    private String type;

    public com.basic.models.response.newmaintenanceSubCatResponse.Record getRecord() {
        return Record;
    }

    public void setRecord(com.basic.models.response.newmaintenanceSubCatResponse.Record Record) {
        this.Record = Record;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Content{" +
                "Record=" + Record +
                ", type='" + type + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.Record, flags);
        dest.writeString(this.type);
    }

    public Content() {
    }

    protected Content(Parcel in) {
        this.Record = in.readParcelable(com.basic.models.response.parcellistresponse.Record.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Creator<com.basic.models.response.newmaintenanceSubCatResponse.Content> CREATOR = new Creator<com.basic.models.response.newmaintenanceSubCatResponse.Content>() {
        @Override
        public com.basic.models.response.newmaintenanceSubCatResponse.Content createFromParcel(Parcel source) {
            return new com.basic.models.response.newmaintenanceSubCatResponse.Content(source);
        }

        @Override
        public com.basic.models.response.newmaintenanceSubCatResponse.Content[] newArray(int size) {
            return new com.basic.models.response.newmaintenanceSubCatResponse.Content[size];
        }
    };
}
