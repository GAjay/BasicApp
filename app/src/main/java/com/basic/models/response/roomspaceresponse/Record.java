package com.basic.models.response.roomspaceresponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 15/9/16.
 */
public class Record {
    @SerializedName("RoomSpaceID")
    private String mRoomSpaceID;

    public String getmRoomSpaceID() {
        return mRoomSpaceID;
    }

    public void setmRoomSpaceID(String mRoomSpaceID) {
        this.mRoomSpaceID = mRoomSpaceID;
    }


}
