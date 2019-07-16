package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class PermissionRequestEntity {
    private String action;
    @SerializedName("entity_id")
    private Long entityId;
    @SerializedName("entity_type")
    private String entityType;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
