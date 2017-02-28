package com.captumia.data;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

public class PackageSubscription extends BasePost {
    public static final Object YOUR_PACKAGES_HEADER = new Object();
    public static final Object PURCHASE_PACKAGES_HEADER = new Object();

    @JsonIgnore
    private int price;
    @JsonIgnore
    private int limit;
    @JsonIgnore
    private int duration;

    @JsonIgnore
    private boolean isUserPackage;

    @Override
    public void setFieldsFromJson(JsonNode node) {
        super.setFieldsFromJson(node);
        price = getIntField(node, "_price", 0);
        limit = getIntField(node, "_job_listing_limit", 0);
        duration = getIntField(node, "_job_listing_duration", 0);
    }

    public PackageSubscription() {
    }

    public boolean isUserPackage() {
        return isUserPackage;
    }

    public void setIsUserPackage(boolean isUserPackage) {
        this.isUserPackage = isUserPackage;
    }

    public int getPrice() {
        return price;
    }

    public int getLimit() {
        return limit;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.price);
        dest.writeInt(this.limit);
        dest.writeByte(this.isUserPackage ? (byte) 1 : (byte) 0);
    }

    protected PackageSubscription(Parcel in) {
        super(in);
        this.price = in.readInt();
        this.limit = in.readInt();
        this.isUserPackage = in.readByte() != 0;
    }

    public static final Creator<PackageSubscription> CREATOR = new Creator<PackageSubscription>() {
        @Override
        public PackageSubscription createFromParcel(Parcel source) {
            return new PackageSubscription(source);
        }

        @Override
        public PackageSubscription[] newArray(int size) {
            return new PackageSubscription[size];
        }
    };
}
