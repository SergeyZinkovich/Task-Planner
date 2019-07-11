package com.taskplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class EventModel implements Parcelable {
    private Long id;
    private Long patternId;
    private String ownerId;

    private String name;
    private String description;

    private String status;

    private Calendar startTime;
    private Calendar endTime;

    private String rrule;

    public EventModel(){}

    public EventModel(String description, Calendar startTime) {
        this.description = description;
        this.startTime = startTime;
    }

    private EventModel(Parcel parcel){
        String[] data = new String[16];
        parcel.readStringArray(data);
        id = Long.valueOf(data[0]);
        patternId = Long.valueOf(data[1]);
        ownerId = data[2];
        name = data[3];
        description = data[4];
        status = data[5];
        int year = Integer.valueOf(data[6]);
        int month = Integer.valueOf(data[7]);
        int day = Integer.valueOf(data[8]);
        int hour = Integer.valueOf(data[9]);
        int minute = Integer.valueOf(data[10]);
        startTime = Calendar.getInstance();
        startTime.set(year, month, day, hour, minute);
        int year1 = Integer.valueOf(data[11]);
        int month1 = Integer.valueOf(data[12]);
        int day1 = Integer.valueOf(data[13]);
        int hour1 = Integer.valueOf(data[14]);
        int minute1 = Integer.valueOf(data[15]);
        endTime = Calendar.getInstance();
        endTime.set(year, month, day, hour, minute);
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

    public Long getPatternId() {
        return patternId;
    }

    public void setPatternId(Long patternId) {
        this.patternId = patternId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRrule() {
        return rrule;
    }

    public void setRrule(String rrule) {
        this.rrule = rrule;
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
        String sId = String.valueOf(id);
        String sPatternId = String.valueOf(patternId);
        String year = String.valueOf(startTime.get(Calendar.YEAR));
        String month = String.valueOf(startTime.get(Calendar.MONTH));
        String day = String.valueOf(startTime.get(Calendar.DATE));
        String hour = String.valueOf(startTime.get(Calendar.HOUR));
        String minute = String.valueOf(startTime.get(Calendar.MINUTE));
        String year1 = String.valueOf(endTime.get(Calendar.YEAR));
        String month1 = String.valueOf(endTime.get(Calendar.MONTH));
        String day1 = String.valueOf(endTime.get(Calendar.DATE));
        String hour1 = String.valueOf(endTime.get(Calendar.HOUR));
        String minute1 = String.valueOf(endTime.get(Calendar.MINUTE));
        dest.writeStringArray(new String[] {sId, sPatternId, ownerId, name, description, status,
                year, month, day, hour, minute, year1, month1, day1, hour1, minute1});
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
