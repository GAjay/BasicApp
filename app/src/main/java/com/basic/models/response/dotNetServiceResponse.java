package com.basic.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 16/9/16.
 */

public class dotNetServiceResponse {

    @SerializedName("content")
    private int mcontent;

    @SerializedName("Status")
    private String mStatus;

    public int getContent ()
    {
        return mcontent;
    }

    public void setContent (int content)
    {
        this.mcontent = content;
    }

    public String getStatus ()
    {
        return mStatus;
    }

    public void setStatus (String Status)
    {
        this.mStatus = Status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+mcontent+", Status = "+mStatus+"]";
    }
}
