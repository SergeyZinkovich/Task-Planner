package com.taskplanner.data.repository;

import android.support.annotation.MainThread;
import android.util.Log;

import com.taskplanner.App;
import com.taskplanner.data.TaskPlannerApi;
import com.taskplanner.data.entity.DeleteResponseEntity;
import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventInstanceResponseEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;
import com.taskplanner.data.entity.EventResponseEntity;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.Completable;
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

    public Single<EventResponseEntity> getAllEvents(){
        return taskPlannerApi.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<EventResponseEntity> getEventsByInterval(Long from, Long to){
        return taskPlannerApi.getEventsByInterval(from, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<EventResponseEntity> saveEvent(EventEntity eventEntity){
        return taskPlannerApi.saveEvent(eventEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<DeleteResponseEntity> deleteEvent(Long id){
        return taskPlannerApi.deleteEvent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<EventResponseEntity> updateEvent(Long id, EventEntity eventEntity){
        return taskPlannerApi.updateEvent(id, eventEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<EventInstanceResponseEntity> getEventsInstance(Long from, Long to) {
        return taskPlannerApi.getEventsInstancesByInterval(from, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
