package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class EventEntity {

    @SerializedName("created_at")
    private long created_at;
    @SerializedName("details")
    private String details;
    @SerializedName("id")
    private long id;
    @SerializedName("location")
    private String location;
    @SerializedName("name")
    private String name;
    @SerializedName("owner_id")
    private String owner_id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
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
