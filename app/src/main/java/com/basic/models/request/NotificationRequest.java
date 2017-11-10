package com.basic.models.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 15/9/16.
 */
public class NotificationRequest {

    @SerializedName("EnteryId")
    private String mEnteryId;

    @SerializedName("DeviceToken")
    private String mDeviceToken;

    @SerializedName("DeviceType")
    private String mDeviceType="ANDROID";

    @SerializedName("AppVersion")
    private String mAppVersion;

    @SerializedName("EmailId")
    private String mEmailId;

    @SerializedName("Name")
    private String mName;

    @Override
    public String toString() {
        return "{" +
                "EnteryId:'" + mEnteryId + '\'' +
                ", DeviceToken:'" + mDeviceToken + '\'' +
                ", DeviceType:'" + mDeviceType + '\'' +
                ", AppVersion:'" + mAppVersion + '\'' +
                ", EmailId:'" + mEmailId + '\'' +
                ", Name:'"+mName+'\''+
                '}';
    }

    public NotificationRequest(String mEnteryId, String mDeviceToken, String mAppVersion, String mEmailId,String name) {
        this.mEnteryId = mEnteryId;
        this.mDeviceToken = mDeviceToken;
        this.mAppVersion = mAppVersion;
        this.mEmailId = mEmailId;
        this.mName = name;
    }
}
