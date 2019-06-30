package com.taskplanner.data.repository;

import android.util.Log;

import com.taskplanner.App;
import com.taskplanner.data.TaskPlannerApi;
import com.taskplanner.data.entity.EventPatternsResponseEntity;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventPatternRepository {

    @Inject
    TaskPlannerApi taskPlannerApi;

    public EventPatternRepository(){
        App.getComponent().inject(this);
    }

    public void getPatterns(){
        taskPlannerApi.getPatterns().enqueue(new Callback<EventPatternsResponseEntity>() {
            @Override
            public void onResponse(Call<EventPatternsResponseEntity> call, Response<EventPatternsResponseEntity> response) {
                EventPatternsResponseEntity entity = response.body();
            }

            @Override
            public void onFailure(Call<EventPatternsResponseEntity> call, Throwable throwable) {
                Log.d("Network error: ", throwable.getMessage());
            }
        });
    }

}
