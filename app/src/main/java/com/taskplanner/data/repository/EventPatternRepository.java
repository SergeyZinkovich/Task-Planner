package com.taskplanner.data.repository;

import android.util.Log;

import com.taskplanner.App;
import com.taskplanner.data.TaskPlannerApi;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class EventPatternRepository {

    @Inject
    TaskPlannerApi taskPlannerApi;

    public EventPatternRepository(){
        App.getComponent().inject(this);
    }

    public void getPatternsByEventsId(Long[] id){
        taskPlannerApi.getPatternsByEventsId(id).enqueue(new Callback<EventPatternsResponseEntity>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventPatternsResponseEntity> call, Response<EventPatternsResponseEntity> response) {
                EventPatternsResponseEntity entity = response.body();
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventPatternsResponseEntity> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }

    public void savePattern(Long id, EventPatternEntity eventPatternEntity){
        taskPlannerApi.savePattern(id, eventPatternEntity).enqueue(new Callback<EventPatternsResponseEntity>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventPatternsResponseEntity> call, Response<EventPatternsResponseEntity> response) {
                EventPatternsResponseEntity entity = response.body();
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventPatternsResponseEntity> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }


    public void updatePattern(Long id, EventPatternEntity eventPatternEntity){
        taskPlannerApi.updatePattern(id, eventPatternEntity).enqueue(new Callback<EventPatternsResponseEntity>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<EventPatternsResponseEntity> call, Response<EventPatternsResponseEntity> response) {
                EventPatternsResponseEntity entity = response.body();
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<EventPatternsResponseEntity> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }

    public void deletePattern(Long id){
        taskPlannerApi.deletePattern(id).enqueue(new Callback<Void>() {
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
}
