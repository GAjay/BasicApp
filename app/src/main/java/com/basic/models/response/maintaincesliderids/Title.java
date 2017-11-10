package com.basic.models.response.maintaincesliderids;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model class to hold the maintenance list web service response.
 */
public class Title implements Parcelable {
    @SerializedName("type")
    private String type;

    @SerializedName("content")
    private String content;


    @Override
    public String toString() {
        return "Title{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

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

    public static final Creator<com.basic.models.response.maintaincesliderids.Title> CREATOR = new Creator<com.basic.models.response.maintaincesliderids.Title>() {
        @Override
        public com.basic.models.response.maintaincesliderids.Title createFromParcel(Parcel source) {
            return new com.basic.models.response.maintaincesliderids.Title(source);
        }

        @Override
        public com.basic.models.response.maintaincesliderids.Title[] newArray(int size) {
            return new com.basic.models.response.maintaincesliderids.Title[size];
        }
    };
}
