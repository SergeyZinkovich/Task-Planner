package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class PermissionEntity {
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("entity_id")
    private String entityId;
    private Long id;
    private String name;
    @SerializedName("owner_id")
    private String ownerId;
    @SerializedName("updated_at")
    private Long updatedAt;
    @SerializedName("user_id")
    private String userId;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
