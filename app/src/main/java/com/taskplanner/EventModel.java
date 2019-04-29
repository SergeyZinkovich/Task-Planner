package com.taskplanner;

public class EventModel {
    private String description;
    private int year;
    private int month;
    private int day;
    private int hour;

    public EventModel(String description, int year, int month, int day, int hour) {
        this.description = description;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
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
}
