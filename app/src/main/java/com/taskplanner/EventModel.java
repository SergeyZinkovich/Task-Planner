package com.taskplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class EventModel implements Parcelable {
    private Long id;
    private String name;        //TODO: починить Parcelable, добавить все поля, геттеры и сеттеры
    private String description;
    private Calendar startTime;
    private Calendar endTime;

    public EventModel(){}

    public EventModel(String description, Calendar startTime) {
        this.description = description;
        this.startTime = startTime;
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
        startTime = Calendar.getInstance();
        startTime.set(year, month, day, hour, minute);
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public int getHour(){
        return startTime.get(Calendar.HOUR_OF_DAY);
    }

    public int getDay(){
        return startTime.get(Calendar.DATE);
    }

    public void setStartTimeInMillis(Long time){
        startTime = Calendar.getInstance();
        startTime.setTimeInMillis(time);
    }

    public void setEndTimeFromDuration(Long duration){
        endTime = Calendar.getInstance();
        Long time = startTime.getTimeInMillis();
        endTime.setTimeInMillis(duration + time);
    }

    public Long getStartTimeInMillis(){
        return startTime.getTimeInMillis();
    }

    public Long getEndTimeInMillis(){
        return endTime.getTimeInMillis();
    }

    public Long getDuration(){
        return startTime.getTimeInMillis() - endTime.getTimeInMillis();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String year = String.valueOf(startTime.get(Calendar.YEAR));
        String month = String.valueOf(startTime.get(Calendar.MONTH));
        String day = String.valueOf(startTime.get(Calendar.DATE));
        String hour = String.valueOf(startTime.get(Calendar.HOUR));
        String minute = String.valueOf(startTime.get(Calendar.MINUTE));
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
