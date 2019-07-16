package com.taskplanner.data;

import com.taskplanner.data.entity.DeleteResponseEntity;
import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventInstanceResponseEntity;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;
import com.taskplanner.data.entity.EventResponseEntity;
import com.taskplanner.data.entity.PermissionRequestEntity;
import com.taskplanner.data.entity.PermissionResponseEntity;

import io.reactivex.Completable;
import io.reactivex.Single;
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

    @GET("/api/v1/events")
    Single<EventResponseEntity> getEvents();

    @GET("/api/v1/events")
    Single<EventResponseEntity> getEventsByIds(@Query("id") Long[] id);

    @GET("/api/v1/events")
    Single<EventResponseEntity> getEventsByInterval(@Query("from") Long from, @Query("to") Long to);

    @POST("/api/v1/events")
    Single<EventResponseEntity> saveEvent(@Body EventEntity eventEntity);

    @DELETE("/api/v1/events/{id}")
    Single<DeleteResponseEntity> deleteEvent(@Path("id") Long id);

    @PATCH("/api/v1/events/{id}")
    Single<EventResponseEntity> updateEvent(@Path("id") Long id, @Body EventEntity eventEntity);

    //EventInstances

    @GET("/api/v1/events/instances")
    Single<EventInstanceResponseEntity> getEventsInstances();

    @GET("/api/v1/events/instances")
    Single<EventInstanceResponseEntity> getEventsInstancesById(@Query("id") Long[] id);

    @GET("/api/v1/events/instances")
    Single<EventInstanceResponseEntity> getEventsInstancesByInterval(@Query("from") Long from, @Query("to") Long to);

    //EventPatterns

    @GET("/api/v1/patterns")
    Single<EventPatternsResponseEntity> getPatternsByEventId(@Query("event_id") Long eventsId);

    @GET("/api/v1/patterns")
    Single<EventPatternsResponseEntity> getPatternsByEventIds(@Query("events") Long[] eventsId);

    @GET("/api/v1/patterns/{id}")
    Single<EventPatternsResponseEntity> getPatternsByPatternId(@Path("id") Long patternsId);

    @POST("/api/v1/patterns")
    Single<EventPatternsResponseEntity> savePattern(@Query("event_id") Long eventId, @Body EventPatternEntity eventEntity);

    @DELETE("/api/v1/patterns/{id}")
    Single<Void> deletePattern(@Path("id") Long id);

    @PATCH("/api/v1/patterns/{id}")
    Single<EventPatternsResponseEntity> updatePattern(@Path("id") Long id, @Body EventPatternEntity eventEntity);

    //Permissions

    @GET("/api/v1/permissions")
    Single<PermissionResponseEntity> getPermissions(@Query("entity_type") String entityType, @Query("mine") boolean mine);

    @POST("/api/v1/share")
    Single<String> createPermissionToken(@Body PermissionRequestEntity[] permissions);

    @GET("/api/v1/share/{token}")
    Single<PermissionResponseEntity> activatePermissionToken(@Query("token") String token);

    @DELETE("/api/v1/permissions/{id}")
    Single<PermissionResponseEntity> deletePermission(@Query("id") Long id);
}
