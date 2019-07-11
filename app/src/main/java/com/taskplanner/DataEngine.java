package com.taskplanner;

import android.util.ArrayMap;
import android.util.Log;

import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;
import com.taskplanner.data.entity.EventResponseEntity;
import com.taskplanner.data.repository.EventPatternRepository;
import com.taskplanner.data.repository.EventRepository;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.disposables.Disposable;

public class DataEngine {

    public interface GetEventCallback{
        public void setEvents(Calendar calendar, ArrayList<EventModel> events);
    }

    public interface DeleteEventCallback{
        public void deleteEventSuccess(boolean success);  //TODO: переименовать?
    }

    private EventRepository eventRepository;
    private EventPatternRepository eventPatternRepository;

    private static final DataEngine INSTANCE = new DataEngine();

    public static DataEngine getInstance(){
        return INSTANCE;
    }

    private DataEngine(){
        eventRepository = new EventRepository();
        eventPatternRepository = new EventPatternRepository();
    }

    public void getEventModels(Calendar from, Calendar to, GetEventCallback getEventCallback){
        Long f = from.getTimeInMillis();
        Long t = to.getTimeInMillis();
        eventRepository.getEventsByInterval(f, t).subscribe(ans -> {
            setEvents(ans, from, getEventCallback);
        }, throwable -> {
            Log.e("Network error in get event:", throwable.getMessage());
        });
    }

    private void setEvents(EventResponseEntity eventResponseEntity, Calendar calendar, GetEventCallback getEventCallback){
        ArrayMap<Long, EventModel> events = new ArrayMap<>();
        for (EventEntity eventEntity: eventResponseEntity.getData()){
            EventModel event = new EventModel();
            event.setId(eventEntity.getId());
            event.setOwnerId(eventEntity.getOwnerId());
            event.setName(eventEntity.getName());
            event.setDescription(eventEntity.getDetails());
            event.setStatus(eventEntity.getStatus());
            events.put(event.getId(), event);
        }
        if(events.size() != 0){
            getPatterns(events, calendar, getEventCallback);
        }
    }

    private void getPatterns(ArrayMap<Long, EventModel> events, Calendar calendar, GetEventCallback getEventCallback){
        Long[] ids = new Long[events.size()];
        int i = 0;
        for (EventModel event: events.values()){
            ids[i] = event.getId();
            i++;
        }
        eventPatternRepository.getPatternsByEventsIds(ids).subscribe(ans -> {
            setPatterns(ans, events, calendar, getEventCallback);
        }, throwable -> {
            Log.e("Network error in get pattern:", throwable.getMessage());
        });
    }

    private void setPatterns(EventPatternsResponseEntity response,ArrayMap<Long, EventModel> events,  //TODO: мб вынести колбек
                             Calendar calendar, GetEventCallback getEventCallback){
        for (EventPatternEntity pattern: response.getData()){
            EventModel ev = events.get(pattern.getEventId());
            ev.setPatternId(pattern.getId());
            ev.setStartTimeInMillis(pattern.getStartedAt());
            ev.setEndTimeFromDuration(pattern.getDuration());
            ev.setRrule(pattern.getRrule());
        }
        getEventCallback.setEvents(calendar, new ArrayList<EventModel>(events.values()));
    }

    public void deleteEvent(EventModel event, DeleteEventCallback deleteEventCallback){
        eventRepository.deleteEvent(event.getId()).subscribe(ans -> {
            deleteEventCallback.deleteEventSuccess(ans.isSuccess());
        }, throwable -> {
            Log.e("Network error in delete event:", throwable.getMessage());
        });
    }

    public void saveEvent(EventModel event, DeleteEventCallback deleteEventCallback){
        eventRepository.saveEvent(convertEventModelToEntity(event)).subscribe(ans -> {
            savePatterns(ans.getData()[0].getId(), event, deleteEventCallback);
        }, throwable -> {
            Log.e("Network error in save event:", throwable.getMessage());
            deleteEventCallback.deleteEventSuccess(false);
        });
    }

    private EventEntity convertEventModelToEntity(EventModel eventModel){
        EventEntity entity = new EventEntity();
        entity.setName(eventModel.getName());
        entity.setDetails(eventModel.getDescription());
        return entity;
    }

    private void savePatterns(Long eventId, EventModel event, DeleteEventCallback deleteEventCallback){
        eventPatternRepository.savePattern(eventId, convertEventModelToPattern(event)).subscribe(ans -> {
            deleteEventCallback.deleteEventSuccess(true);
        }, throwable -> {
            Log.e("Network error in save pattern:", throwable.getMessage());
            deleteEventCallback.deleteEventSuccess(false);
        });
    }

    private EventPatternEntity convertEventModelToPattern(EventModel event){
        EventPatternEntity pattern = new EventPatternEntity();
        pattern.setStartedAt(event.getStartTimeInMillis());
        pattern.setDuration(event.getDuration());
        pattern.setEndedAt(event.getEndTimeInMillis());
        return pattern;
    }

    public void updateEvent(EventModel event, DeleteEventCallback deleteEventCallback){
        eventRepository.updateEvent(event.getId(), convertEventModelToEntity(event)).subscribe(ans -> {
            updatePattern(event, deleteEventCallback);
        }, throwable -> {
            Log.e("Network error in update event:", throwable.getMessage());
            deleteEventCallback.deleteEventSuccess(false);
        });
    }

    private void updatePattern(EventModel event, DeleteEventCallback deleteEventCallback){
        eventPatternRepository.updatePattern(event.getPatternId(), convertEventModelToPattern(event)).subscribe(ans -> {
            deleteEventCallback.deleteEventSuccess(true);
        }, throwable -> {
            Log.e("Network error in update pattern:", throwable.getMessage());
            deleteEventCallback.deleteEventSuccess(false);
        });
    }
}
