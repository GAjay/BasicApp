package com.basic.models.response.priorityidResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 15/9/16.
 */
public class Record {
    @SerializedName("Description")
    private String mDescription;

    @SerializedName("PriorityID")
    private String mPriorityID;

    @SerializedName("SortOrder")
    private String mSortOrder;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPriorityID() {
        return mPriorityID;
    }

    public void setmPriorityID(String mPriorityID) {
        this.mPriorityID = mPriorityID;
    }

    public String getmSortOrder() {
        return mSortOrder;
    }

    public void setmSortOrder(String mSortOrder) {
        this.mSortOrder = mSortOrder;
    }

    @Override
    public String toString() {
        return "Record{" +
                "mDescription='" + mDescription + '\'' +
                ", mPriorityID='" + mPriorityID + '\'' +
                ", mSortOrder='" + mSortOrder + '\'' +
                '}';
    }
}
