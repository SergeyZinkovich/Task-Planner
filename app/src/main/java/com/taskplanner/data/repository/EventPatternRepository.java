package com.taskplanner.data.repository;

import android.util.Log;

import com.taskplanner.App;
import com.taskplanner.data.TaskPlannerApi;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    public Single<EventPatternsResponseEntity> getPatternsByEventsId(Long id){
        return taskPlannerApi.getPatternsByEventId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<EventPatternsResponseEntity> getPatternsByEventsIds(Long[] id){
        return taskPlannerApi.getPatternsByEventIds(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<EventPatternsResponseEntity> savePattern(Long id, EventPatternEntity eventPatternEntity){
        return taskPlannerApi.savePattern(id, eventPatternEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<EventPatternsResponseEntity> updatePattern(Long id, EventPatternEntity eventPatternEntity){
        return taskPlannerApi.updatePattern(id, eventPatternEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Void> deletePattern(Long id){
        return taskPlannerApi.deletePattern(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
