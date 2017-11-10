package com.basic.models.response.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Title implements Parcelable {
    @SerializedName("type")
    private String type;

    @SerializedName("content")
    private String content;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.content);
    }

    public Title() {
    }

    protected Title(Parcel in) {
        this.type = in.readString();
        this.content = in.readString();
    }

    public static final Creator<com.basic.models.response.location.Title> CREATOR = new Creator<com.basic.models.response.location.Title>() {
        @Override
        public com.basic.models.response.location.Title createFromParcel(Parcel source) {
            return new com.basic.models.response.location.Title(source);
        }

        @Override
        public com.basic.models.response.location.Title[] newArray(int size) {
            return new com.basic.models.response.location.Title[size];
        }
    };
}
