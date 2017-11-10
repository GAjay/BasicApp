package com.basic.models.response.priorityidResponse;

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
    private String mContent;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mType);
        dest.writeString(this.mContent);
    }

    public Title() {
    }

    protected Title(Parcel in) {
        this.mType = in.readString();
        this.mContent = in.readString();
    }

    public static final Creator<Title> CREATOR = new Creator<Title>() {
        @Override
        public Title createFromParcel(Parcel source) {
            return new Title(source);
        }

        @Override
        public Title[] newArray(int size) {
            return new Title[size];
        }
    };
}
