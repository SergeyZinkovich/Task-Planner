package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class EventInstanceEntity {
    @SerializedName("ended_at")
    private long ended_at;
    @SerializedName("event_id")
    private long event_id;
    @SerializedName("pattern_id")
    private long pattern_id;
    @SerializedName("started_at")
    private long started_at;

    public long getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(long ended_at) {
        this.ended_at = ended_at;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public long getPattern_id() {
        return pattern_id;
    }

    public void setPattern_id(long pattern_id) {
        this.pattern_id = pattern_id;
    }

    public long getStarted_at() {
        return started_at;
    }

    public void setStarted_at(long started_at) {
        this.started_at = started_at;
    }
}
