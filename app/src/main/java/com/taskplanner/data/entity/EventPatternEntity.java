package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

import java.security.Timestamp;


public class EventPatternEntity {
    @SerializedName("created_at")
    private Long createdAt;
    private Long duration;
    @SerializedName("ended_at")
    private Long endedAt;
    @SerializedName("event_id")
    Long eventId;
    private String exrule;
    private EventPatternExruleEntity[] exrules;
    private Long id;
    private String rrule;
    @SerializedName("started_at")
    private Long startedAt;
    private String timezone;
    @SerializedName("updated_at")
    private Long updatedAt;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Long endedAt) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRrule() {
        return rrule;
    }

    public void setRrule(String rrule) {
        this.rrule = rrule;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
