package com.taskplanner;

import android.util.ArrayMap;
import android.util.Log;
import android.util.TimeFormatException;

import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventInstanceEntity;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.entity.EventPatternsResponseEntity;
import com.taskplanner.data.entity.EventResponseEntity;
import com.taskplanner.data.repository.EventPatternRepository;
import com.taskplanner.data.repository.EventRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class DataEngine {

    public interface GetEventCallback{
        void setEvents(Calendar calendar, ArrayList<EventModel> events);
    }

    public interface RequestEventCallback {
        void requestEventSuccess(boolean success);
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

    public void getEventInstances(Calendar from, Calendar to, GetEventCallback getEventCallback){
        Long f = from.getTimeInMillis();
        Long t = to.getTimeInMillis();
        eventRepository.getEventsInstance(f, t).subscribe(
                response -> getEventModels(response.getData(), from, getEventCallback),
                throwable -> Log.e("Network error in get instance:", throwable.getMessage())
        );
    }

    private void getEventModels(EventInstanceEntity[] instances, Calendar from,
                                GetEventCallback getEventCallback){
        Long[] ids = new Long[instances.length];
        for (int i = 0; i < instances.length; i++){
                ids[i] = instances[i].getEventId();
        }
        if(ids.length == 0){
            return;
        }
        eventRepository.getEventsByIds(ids).subscribe(
                response -> getPatterns(convertEntityToEventModels(response, instances), from, getEventCallback),
                throwable -> Log.e("Network error in get event:", throwable.getMessage())
        );
    }

    private ArrayMap<Long, EventModel> convertEntityToEventModels(EventResponseEntity eventResponseEntity,
                                                                  EventInstanceEntity[] instances){
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
        for (EventInstanceEntity instance: instances){
            EventModel ev = events.get(instance.getEventId());
            ev.setStartTimeInMillis(instance.getStartedAt());
            ev.setEndTimeInMillis(instance.getEndedAt());
        }
        return events;
    }

    private void getPatterns(ArrayMap<Long, EventModel> events, Calendar calendar, GetEventCallback getEventCallback){
        if (events.size() ==0){
            return;
        }
        Long[] ids = new Long[events.size()];
        int i = 0;
        for (EventModel event: events.values()){
            ids[i] = event.getId();
            i++;
        }
        eventPatternRepository.getPatternsByEventsIds(ids).subscribe(
                response -> getEventCallback.setEvents(calendar, addPatternsToEventModels(response, events)),
                throwable -> Log.e("Network error in get pattern:", throwable.getMessage())
        );
    }

    private ArrayList<EventModel> addPatternsToEventModels(EventPatternsResponseEntity response, ArrayMap<Long, EventModel> events){
        for (EventPatternEntity pattern: response.getData()){
            EventModel ev = events.get(pattern.getEventId());
            ev.setPatternId(pattern.getId());
            ev.setRrule(pattern.getRrule());
        }
        return new ArrayList<>(events.values());
    }

    public void deleteEvent(EventModel event, RequestEventCallback requestEventCallback){
        eventRepository.deleteEvent(event.getId()).subscribe(
                response -> requestEventCallback.requestEventSuccess(response.isSuccess()),
                throwable -> Log.e("Network error in delete event:", throwable.getMessage())
        );
    }

    public void saveEvent(EventModel event, RequestEventCallback requestEventCallback){
        eventRepository.saveEvent(convertEventModelToEntity(event)).subscribe(
                response -> savePatterns(response.getData()[0].getId(), event, requestEventCallback),
                throwable -> {
                    Log.e("Network error in save event:", throwable.getMessage());
                    requestEventCallback.requestEventSuccess(false);
                });
    }

    private EventEntity convertEventModelToEntity(EventModel eventModel){
        EventEntity entity = new EventEntity();
        entity.setName(eventModel.getName());
        entity.setDetails(eventModel.getDescription());
        return entity;
    }

    private void savePatterns(Long eventId, EventModel event, RequestEventCallback requestEventCallback){
        eventPatternRepository.savePattern(eventId, convertEventModelToPattern(event)).subscribe(
                response -> requestEventCallback.requestEventSuccess(true),
                throwable -> {
                    Log.e("Network error in save pattern:", throwable.getMessage());
                    requestEventCallback.requestEventSuccess(false);
                });
    }

    private EventPatternEntity convertEventModelToPattern(EventModel event){
        EventPatternEntity pattern = new EventPatternEntity();
        pattern.setStartedAt(event.getStartTimeInMillis());
        pattern.setDuration(event.getDuration());
        pattern.setEndedAt(event.getEndTimeInMillis());
        return pattern;
    }

    public void updateEvent(EventModel event, RequestEventCallback requestEventCallback){
        eventRepository.updateEvent(event.getId(), convertEventModelToEntity(event)).subscribe(
                response -> updatePattern(event, requestEventCallback),
                throwable -> {
                    Log.e("Network error in update event:", throwable.getMessage());
                    requestEventCallback.requestEventSuccess(false);
                });
    }

    private void updatePattern(EventModel event, RequestEventCallback requestEventCallback){
        eventPatternRepository.updatePattern(event.getPatternId(), convertEventModelToPattern(event)).subscribe(
                response -> requestEventCallback.requestEventSuccess(true),
                throwable -> {
                    Log.e("Network error in update pattern:", throwable.getMessage());
                    requestEventCallback.requestEventSuccess(false);
                });
    }
}
