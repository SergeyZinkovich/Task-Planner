package com.taskplanner.data;

import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventInstanceResponseEntity;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;
import com.taskplanner.data.entity.EventResponseEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskPlannerApi {

    //Events

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/events")
    Call<EventResponseEntity> getEvents();

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/events")
    Call<EventResponseEntity> getEventsById(@Query("id") Long[] id);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/events")
    Call<EventResponseEntity> getEventsByInterval(@Query("from") Long from, @Query("to") Long to);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @POST("/api/v1/events")
    Call<EventResponseEntity> saveEvent(@Body EventEntity eventEntity);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @DELETE("/api/v1/events/{id}")
    Call<Void> deleteEvent(@Path("id") Long id);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @PATCH("/api/v1/events/{id}")
    Call<EventResponseEntity> updateEvent(@Path("id") Long id, @Body EventEntity eventEntity);

    //EventInstances

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/events/instances")
    Call<EventInstanceResponseEntity> getEventsInstances();

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/events/instances")
    Call<EventInstanceResponseEntity> getEventsInstancesById(@Query("id") Long[] id);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/events/instances")
    Call<EventInstanceResponseEntity> getEventsInstancesByInterval(@Query("from") Long from, @Query("to") Long to);

    //EventPatterns

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/patterns")
    Call<EventPatternsResponseEntity> getPatternsByEventsId(@Query("event_id") Long[] eventsId);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @GET("/api/v1/patterns/{id}")
    Call<EventPatternsResponseEntity> getPatternsByPatternsId(@Path("id") Long[] patternsId);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @POST("/api/v1/patterns")
    Call<EventPatternsResponseEntity> savePattern(@Query("event_id") Long eventId, @Body EventPatternEntity eventEntity);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @DELETE("/api/v1/patterns/{id}")
    Call<Void> deletePattern(@Path("id") Long id);

    @Headers({"X-Firebase-Auth: serega_mem"})
    @PATCH("/api/v1/patterns/{id}")
    Call<EventPatternsResponseEntity> updatePattern(@Path("id") Long id, @Body EventPatternEntity eventEntity);

}
