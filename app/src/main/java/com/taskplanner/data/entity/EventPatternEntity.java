package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

import java.security.Timestamp;


public class EventPatternEntity {
    @SerializedName("created_at")
    private Timestamp createdAt;
    @SerializedName("day")
    private String day;
    @SerializedName("duration")
    private int duration;
    @SerializedName("ended_at")
    private String endedAt;
    @SerializedName("hour")
    private String hour;
    @SerializedName("id")
    private int id;
    @SerializedName("minute")
    private String minute;
    @SerializedName("month")
    private String month;
    @SerializedName("started_at")
    private String startedAt;
    @SerializedName("type")
    private int type;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("weekday")
    private String weekday;
    @SerializedName("year")
    private String year;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
