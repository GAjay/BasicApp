package com.basic.models.response.loginresponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 15/9/16.
 */
public class Record {
    @SerializedName("EntryID")
    private String mEntryID;

    @SerializedName("PinNumber")
    private String mPinNumber;

    @SerializedName("Email")
    private String mEmail;

    @SerializedName("NameLast")
    private String mNameLast;

    @SerializedName("NameFirst")
    private String mNameFirst;

    @SerializedName("NameTitle")
    private String mNameTitle;

    @SerializedName("RoomSpaceID")
    private String roomSpaceId;

    @SerializedName("RoomLocationID")
    private String roomLocationId;

    public String getRoomLocationId() {
        return roomLocationId;
    }

    public void setRoomLocationId(String roomLocationId) {
        this.roomLocationId = roomLocationId;
    }

    public String getEntryID() {
        return mEntryID;
    }

    public void setEntryID(String entryID) {
        this.mEntryID = entryID;
    }

    public String getPinNumber() {
        return mPinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.mPinNumber = pinNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getNameLast() {
        return mNameLast;
    }

    public void setNameLast(String nameLast) {
        this.mNameLast = nameLast;
    }

    public String getNameFirst() {
        return mNameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.mNameFirst = nameFirst;
    }

    public String getNameTitle() {
        return mNameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.mNameTitle = nameTitle;
    }

    public String getRoomSpaceId() {
        return roomSpaceId;
    }

    public void setRoomSpaceId(String roomSpaceId) {
        this.roomSpaceId = roomSpaceId;
    }

    @Override
    public String toString() {
        return "Record{" +
                "entryID='" + mEntryID + '\'' +
                ", pinNumber='" + mPinNumber + '\'' +
                ", email='" + mEmail + '\'' +
                ", nameLast='" + mNameLast + '\'' +
                ", nameFirst='" + mNameFirst + '\'' +
                ", nameTitle='" + mNameTitle + '\'' +
                '}';
    }
}
