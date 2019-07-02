package com.taskplanner.data.repository;

import android.util.Log;

import com.taskplanner.App;
import com.taskplanner.data.TaskPlannerApi;
import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;
import com.taskplanner.data.entity.EventResponseEntity;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class EventRepository {

    @Inject
    TaskPlannerApi taskPlannerApi;

    public EventRepository(){
        App.getComponent().inject(this);
    }

    public void getEvents(){
        taskPlannerApi.getEvents().enqueue(new Callback<EventResponseEntity>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventResponseEntity> call, Response<EventResponseEntity> response) {
                EventResponseEntity entity = response.body();
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventResponseEntity> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }

    public void saveEvent(EventEntity eventEntity){
        taskPlannerApi.saveEvent(eventEntity).enqueue(new Callback<EventResponseEntity>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventResponseEntity> call, Response<EventResponseEntity> response) {
                EventResponseEntity entity = response.body();
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventResponseEntity> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }

    public void deleteEvent(Long id){
        taskPlannerApi.deleteEvent(id).enqueue(new Callback<Void>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }

    public void updateEvent(Long id, EventEntity eventEntity){
        taskPlannerApi.updateEvent(id, eventEntity).enqueue(new Callback<EventResponseEntity>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventResponseEntity> call, Response<EventResponseEntity> response) {
                EventResponseEntity entity = response.body();
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventResponseEntity> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }
}
