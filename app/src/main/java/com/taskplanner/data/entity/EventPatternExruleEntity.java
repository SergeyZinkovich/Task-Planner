package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class EventPatternExruleEntity {
    @SerializedName("created_at")
    private long created_at;
    @SerializedName("id")
    private long id;
    @SerializedName("rule")
    private String rule;
    @SerializedName("updated_at")
    private long updated_at;

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }
}
