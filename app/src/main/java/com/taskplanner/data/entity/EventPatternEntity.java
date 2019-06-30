package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

import java.security.Timestamp;


public class EventPatternEntity {
    @SerializedName("created_at")
    private long createdAt;
    @SerializedName("duration")
    private long duration;
    @SerializedName("ended_at")
    private long endedAt;
    @SerializedName("exrule")
    private String exrule;
    @SerializedName("exrules")
    private EventPatternExruleEntity[] exrules;
    @SerializedName("id")
    private long id;
    @SerializedName("rrule")
    private String rrule;
    @SerializedName("started_at")
    private long startedAt;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("updated_at")
    private String updatedAt;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(long endedAt) {
        this.endedAt = endedAt;
    }

    public String getExrule() {
        return exrule;
    }

    public void setExrule(String exrule) {
        this.exrule = exrule;
    }

    public EventPatternExruleEntity[] getExrules() {
        return exrules;
    }

    public void setExrules(EventPatternExruleEntity[] exrules) {
        this.exrules = exrules;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRrule() {
        return rrule;
    }

    public void setRrule(String rrule) {
        this.rrule = rrule;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
