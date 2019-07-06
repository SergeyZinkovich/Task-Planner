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
            Log.e("Network error:", throwable.getMessage());
        });
    }

    private void setEvents(EventResponseEntity eventResponseEntity, Calendar calendar, GetEventCallback getEventCallback){
        ArrayMap<Long, EventModel> events = new ArrayMap<>();
        for (EventEntity eventEntity: eventResponseEntity.getData()){
            EventModel event = new EventModel();
            event.setId(eventEntity.getId());
            event.setName(eventEntity.getName());
            event.setDescription(eventEntity.getDetails());
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
            Log.e("Network error:", throwable.getMessage());
        });
    }

    private void setPatterns(EventPatternsResponseEntity response,ArrayMap<Long, EventModel> events,
                             Calendar calendar, GetEventCallback getEventCallback){
        for (EventPatternEntity pattern: response.getData()){
            EventModel ev = events.get(pattern.getEventId());
            ev.setStartTimeInMillis(pattern.getStartedAt());
            ev.setEndTimeFromDuration(pattern.getDuration());
        }
        getEventCallback.setEvents(calendar, new ArrayList<EventModel>(events.values()));
    }

}
