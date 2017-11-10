package com.basic.models.response.loginresponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 15/9/16.
 */
public class Content {

    @SerializedName("type")
    public String mType;

    @SerializedName("Record")
    public com.basic.models.response.loginresponse.Record mRecord;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public com.basic.models.response.loginresponse.Record getRecord() {
        return mRecord;
    }

    public void setRecord(Record record) {
        this.mRecord = record;
    }

    @Override
    public String toString() {
        return "Content{" +
                "type='" + mType + '\'' +
                ", record=" + mRecord +
                '}';
    }
}
