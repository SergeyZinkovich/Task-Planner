package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class PermissionEntity {
    @SerializedName("created_at")
    private long created_at;
    @SerializedName("entity_id")
    private String entity_id;
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("owner_id")
    private String owner_id;
    @SerializedName("updated_at")
    private long updated_at;
    @SerializedName("user_id")
    private String user_id;

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
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

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
