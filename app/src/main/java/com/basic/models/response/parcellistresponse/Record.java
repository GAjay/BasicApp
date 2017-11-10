package com.basic.models.response.parcellistresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vikas on 15/9/16.
 */
public class Record implements Parcelable {
    @SerializedName("EntryParcelID")
    private int entryParcelId;
    @SerializedName("parcel_type_val")
    private String parcelTypeValue;
    @SerializedName("shipping_type_val")
    private String shippingTypeValue;
    @SerializedName("AddressTypeID")
    private int addressTypeId;
    @SerializedName("ParcelStatusEnum")
    private int parcelStatusEnum;
    @SerializedName("status_desc")
    private String statusDescription;
    @SerializedName("IssueDate")
    private String issueDate;
    @SerializedName("ReceiptDate")
    private String receiptDate;
    @SerializedName("TrackingNumber")
    private String trackingNumber;
    @SerializedName("Comments")
    private String comments;
    @SerializedName("address_val")
    private String addressValue;
    @SerializedName("Description")
    private String description;


    @Override
    public String toString() {
        return "Record{" +
                "entryParcelId=" + entryParcelId +
                ", parcelTypeValue='" + parcelTypeValue + '\'' +
                ", shippingTypeValue='" + shippingTypeValue + '\'' +
                ", addressTypeId=" + addressTypeId +
                ", parcelStatusEnum=" + parcelStatusEnum +
                ", statusDescription='" + statusDescription + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", receiptDate='" + receiptDate + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", comments='" + comments + '\'' +
                ", addressValue='" + addressValue + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getEntryParcelId() {
        return entryParcelId;
    }

    public String getParcelTypeValue() {
        return parcelTypeValue;
    }

    public String getShippingTypeValue() {
        return shippingTypeValue;
    }

    public int getAddressTypeId() {
        return addressTypeId;
    }

    public int getParcelStatusEnum() {
        return parcelStatusEnum;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getComments() {
        return comments;
    }

    public String getAddressValue() {
        return addressValue;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.entryParcelId);
        dest.writeString(this.parcelTypeValue);
        dest.writeString(this.shippingTypeValue);
        dest.writeInt(this.addressTypeId);
        dest.writeInt(this.parcelStatusEnum);
        dest.writeString(this.statusDescription);
        dest.writeString(this.issueDate);
        dest.writeString(this.receiptDate);
        dest.writeString(this.trackingNumber);
        dest.writeString(this.comments);
        dest.writeString(this.addressValue);
        dest.writeString(this.description);
    }

    public Record() {
    }

    protected Record(Parcel in) {
        this.entryParcelId = in.readInt();
        this.parcelTypeValue = in.readString();
        this.shippingTypeValue = in.readString();
        this.addressTypeId = in.readInt();
        this.parcelStatusEnum = in.readInt();
        this.statusDescription = in.readString();
        this.issueDate = in.readString();
        this.receiptDate = in.readString();
        this.trackingNumber = in.readString();
        this.comments = in.readString();
        this.addressValue = in.readString();
        this.description = in.readString();
    }

    public static final Creator<com.basic.models.response.parcellistresponse.Record> CREATOR = new Creator<com.basic.models.response.parcellistresponse.Record>() {
        @Override
        public com.basic.models.response.parcellistresponse.Record createFromParcel(Parcel source) {
            return new com.basic.models.response.parcellistresponse.Record(source);
        }

        @Override
        public com.basic.models.response.parcellistresponse.Record[] newArray(int size) {
            return new com.basic.models.response.parcellistresponse.Record[size];
        }
    };
}
