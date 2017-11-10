package com.basic.models.response.searchresource;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Content implements Parcelable {

    @SerializedName("Record")
    private com.basic.models.response.searchresource.Record Record;

    @SerializedName("type")
    private String type;

    public com.basic.models.response.searchresource.Record getRecord() {
        return Record;
    }

    public void setRecord(com.basic.models.response.searchresource.Record Record) {
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
        this.Record = in.readParcelable(com.basic.models.response.searchresource.Record.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Creator<com.basic.models.response.searchresource.Content> CREATOR = new Creator<com.basic.models.response.searchresource.Content>() {
        @Override
        public com.basic.models.response.searchresource.Content createFromParcel(Parcel source) {
            return new com.basic.models.response.searchresource.Content(source);
        }

        @Override
        public com.basic.models.response.searchresource.Content[] newArray(int size) {
            return new com.basic.models.response.searchresource.Content[size];
        }
    };
}

