package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;
import com.taskplanner.data.repository.EventPatternRepository;

public class EventPatternsResponseEntity {
    private int count;
    private EventPatternEntity[] data;
    private String message;
    private Long offset;
    private int status;
    private boolean success;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public EventPatternEntity[] getData() {
        return data;
    }

    public void setData(EventPatternEntity[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
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
