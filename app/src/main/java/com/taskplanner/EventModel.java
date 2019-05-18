package com.taskplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class EventModel implements Parcelable {
    private String description;
    private Calendar calendar;

    public EventModel(String description, Calendar calendar) {
        this.description = description;
        this.calendar = calendar;
    }

    private EventModel(Parcel parcel){
        String[] data = new String[6];
        parcel.readStringArray(data);
        description = data[0];
        int year = Integer.valueOf(data[1]);
        int month = Integer.valueOf(data[2]);
        int day = Integer.valueOf(data[3]);
        int hour = Integer.valueOf(data[4]);
        int minute = Integer.valueOf(data[5]);
        calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getDescription() {
        return description;
    }

    public int getHour(){
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getDay(){
        return calendar.get(Calendar.DATE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String hour = String.valueOf(calendar.get(Calendar.HOUR));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        dest.writeStringArray(new String[] {description, year, month, day, hour, minute});
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
