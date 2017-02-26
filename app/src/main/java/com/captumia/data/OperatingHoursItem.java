package com.captumia.data;

import android.os.Parcel;
import android.os.Parcelable;

public class OperatingHoursItem implements Parcelable {
    private String startTime;
    private String endTime;
    private int day;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeInt(this.day);
    }

    public OperatingHoursItem() {
    }

    protected OperatingHoursItem(Parcel in) {
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.day = in.readInt();
    }

    public static final Parcelable.Creator<OperatingHoursItem> CREATOR = new Parcelable.Creator<OperatingHoursItem>() {
        @Override
        public OperatingHoursItem createFromParcel(Parcel source) {
            return new OperatingHoursItem(source);
        }

        @Override
        public OperatingHoursItem[] newArray(int size) {
            return new OperatingHoursItem[size];
        }
    };
}
