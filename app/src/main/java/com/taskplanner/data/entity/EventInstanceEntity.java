package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class EventInstanceEntity {
    @SerializedName("ended_at")
    private Long endedAt;
    @SerializedName("event_id")
    private Long eventId;
    @SerializedName("pattern_id")
    private Long patternId;
    @SerializedName("started_at")
    private Long startedAt;

    public Long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Long endedAt) {
        this.endedAt = endedAt;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getPatternId() {
        return patternId;
    }

    public void setPatternId(Long patternId) {
        this.patternId = patternId;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }
}
