package com.basic.models.response.loginresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vikas on 15/9/16.
 */
public class Title implements Parcelable {
    @SerializedName("type")
    private String mType;

    @SerializedName("content")
    private int mContent;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mType);
        dest.writeInt(this.mContent);
    }

    public Title() {
    }

    protected Title(Parcel in) {
        this.mType = in.readString();
        this.mContent = in.readInt();
    }

    public static final Creator<com.basic.models.response.loginresponse.Title> CREATOR = new Creator<com.basic.models.response.loginresponse.Title>() {
        @Override
        public com.basic.models.response.loginresponse.Title createFromParcel(Parcel source) {
            return new com.basic.models.response.loginresponse.Title(source);
        }

        @Override
        public com.basic.models.response.loginresponse.Title[] newArray(int size) {
            return new com.basic.models.response.loginresponse.Title[size];
        }
    };
}
