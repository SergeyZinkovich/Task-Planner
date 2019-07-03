package com.taskplanner;

import android.app.Application;

import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.repository.EventPatternRepository;
import com.taskplanner.data.repository.EventRepository;

public class App extends Application {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.create();
        EventRepository eventRepository = new EventRepository();
        eventRepository.getEvents();
        EventEntity eventEntity = new EventEntity();  //TODO: удалить дебаг
        eventEntity.setDetails("details");
        eventEntity.setLocation("Location");
        eventEntity.setName("TestUpdate");
        eventEntity.setStatus("testPost");
        //eventRepository.saveEvent(eventEntity);
        //eventRepository.deleteEvent(441L);
        //eventRepository.updateEvent(441L, eventEntity);
        EventPatternRepository patternRepository = new EventPatternRepository();
        //patternRepository.getPatternsByEventsId(new Long[]{384L});
        EventPatternEntity eventPatternEntity= new EventPatternEntity();
        eventPatternEntity.setStartedAt(1556712345000L);
        eventPatternEntity.setEndedAt(1556712345000L);
        eventPatternEntity.setDuration(0L);
        eventPatternEntity.setExrule("");
        //patternRepository.savePattern(443L, eventPatternEntity);
        //patternRepository.updatePattern(421L, eventPatternEntity);
        //patternRepository.deletePattern(421L);
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
