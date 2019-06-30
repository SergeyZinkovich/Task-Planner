package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class TaskEntity {
    @SerializedName("created_at")
    private long created_at;
    @SerializedName("deadline_at")
    private long deadline_at;
    @SerializedName("details")
    private String details;
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("parent_id")
    private long parent_id;
    @SerializedName("status")
    private String status;
    @SerializedName("updated_at")
    private long updated_at;

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getDeadline_at() {
        return deadline_at;
    }

    public void setDeadline_at(long deadline_at) {
        this.deadline_at = deadline_at;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }
}
