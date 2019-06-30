package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;

public class PermissionResponseEntity {
    @SerializedName("count")
    private int count;
    @SerializedName("data")
    private PermissionEntity[] data;
    @SerializedName("message")
    private String message;
    @SerializedName("offset")
    private long offset;
    @SerializedName("status")
    private int status;
    @SerializedName("success")
    private boolean success;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PermissionEntity[] getData() {
        return data;
    }

    public void setData(PermissionEntity[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
