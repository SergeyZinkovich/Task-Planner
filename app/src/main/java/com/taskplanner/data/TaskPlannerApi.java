package com.taskplanner.data;

import com.taskplanner.data.entity.EventPatternsResponseEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface TaskPlannerApi {

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/patterns")
    Call<EventPatternsResponseEntity> getPatterns();

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/events")
    Call<EventPatternsResponseEntity> getEvents();

}
