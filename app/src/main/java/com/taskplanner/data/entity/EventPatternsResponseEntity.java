package com.taskplanner.data.entity;

import com.google.gson.annotations.SerializedName;
import com.taskplanner.data.repository.EventPatternRepository;

public class EventPatternsResponseEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private EventPatternEntity[] data;

    @SerializedName("message")
    private String message;
    @SerializedName("offset")
    private int offset;

    @SerializedName("success")
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
