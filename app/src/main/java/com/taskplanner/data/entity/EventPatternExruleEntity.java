package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class EventPatternExruleEntity {
    @SerializedName("created_at")
    private Long createdAt;
    private Long id;
    private String rule;
    @SerializedName("updated_at")
    private Long updatedAt;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
