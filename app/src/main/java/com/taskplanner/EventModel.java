package com.taskplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class EventModel implements Parcelable {
    private String description;
    private int year;//TODO: переписать на Date/Calendar
    private int month;
    private int day;
    private int hour;//TODO: добавить минуты

    public EventModel(String description, int year, int month, int day, int hour) {
        this.description = description;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    private EventModel(Parcel parcel){
        String[] data = new String[5];
        parcel.readStringArray(data);
        description = data[0];
        this.year = Integer.valueOf(data[1]);
        this.month = Integer.valueOf(data[2]);
        this.day = Integer.valueOf(data[3]);
        this.hour = Integer.valueOf(data[4]);
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
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
        dest.writeStringArray(new String[] {description, String.valueOf(year), String.valueOf(month),
                String.valueOf(day), String.valueOf(hour)});
    }

    public static final Parcelable.Creator<EventModel> CREATOR = new Parcelable.Creator<EventModel>() {

        @Override
        public EventModel createFromParcel(Parcel source) {
            return new EventModel(source);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };
}
