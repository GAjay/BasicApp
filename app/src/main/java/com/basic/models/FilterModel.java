package com.basic.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vikas on 17/9/16.
 */
public class FilterModel implements Parcelable {
    private String filterName;
    private int filterId;
    private boolean isChecked;

    public FilterModel(String filterName, int filterId, boolean isChecked) {
        this.filterName = filterName;
        this.filterId = filterId;
        this.isChecked = isChecked;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filterName);
        dest.writeInt(this.filterId);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected FilterModel(Parcel in) {
        this.filterName = in.readString();
        this.filterId = in.readInt();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<com.basic.models.FilterModel> CREATOR = new Creator<com.basic.models.FilterModel>() {
        @Override
        public com.basic.models.FilterModel createFromParcel(Parcel source) {
            return new com.basic.models.FilterModel(source);
        }

        @Override
        public com.basic.models.FilterModel[] newArray(int size) {
            return new com.basic.models.FilterModel[size];
        }
    };

    @Override
    public String toString() {
        return "FilterModel{" +
                "filterName='" + filterName + '\'' +
                ", filterId=" + filterId +
                ", isChecked=" + isChecked +
                '}';
    }
}
