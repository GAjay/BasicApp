package com.basic.models.response.parcellistresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vikas on 15/9/16.
 */
public class Title implements Parcelable {
    @SerializedName("type")
    private String type;

    @SerializedName("content")
    private int content;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeInt(this.content);
    }

    public Title() {
    }

    protected Title(Parcel in) {
        this.type = in.readString();
        this.content = in.readInt();
    }

    public static final Creator<com.basic.models.response.parcellistresponse.Title> CREATOR = new Creator<com.basic.models.response.parcellistresponse.Title>() {
        @Override
        public com.basic.models.response.parcellistresponse.Title createFromParcel(Parcel source) {
            return new com.basic.models.response.parcellistresponse.Title(source);
        }

        @Override
        public com.basic.models.response.parcellistresponse.Title[] newArray(int size) {
            return new com.basic.models.response.parcellistresponse.Title[size];
        }
    };
}
