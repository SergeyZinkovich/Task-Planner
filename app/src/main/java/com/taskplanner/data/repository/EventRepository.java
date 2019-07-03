package com.taskplanner.data.repository;

import android.support.annotation.MainThread;
import android.util.Log;

import com.taskplanner.App;
import com.taskplanner.data.TaskPlannerApi;
import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;
import com.taskplanner.data.entity.EventResponseEntity;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    public Single<EventResponseEntity> getEvents(){
        return taskPlannerApi.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    /*enqueue(new Callback<EventResponseEntity>() {
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
        });*/
    }

    public Single<EventResponseEntity> saveEvent(EventEntity eventEntity){
        return taskPlannerApi.saveEvent(eventEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    /*.enqueue(new Callback<EventResponseEntity>() {
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
        });*/
    }

    public Single<Void> deleteEvent(Long id){
        return taskPlannerApi.deleteEvent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());//TODO: протестировать
    /*.enqueue(new Callback<Void>() {
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
        });*/
    }

    public Single<EventResponseEntity> updateEvent(Long id, EventEntity eventEntity){
        return taskPlannerApi.updateEvent(id, eventEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    /*.enqueue(new Callback<EventResponseEntity>() {
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
        });*/
    }
}
