package com.taskplanner;

import java.util.ArrayList;
import java.util.Date;

public class Mockup {

    private static final Mockup INSTANCE = new Mockup();

    private ArrayList<EventModel> events = new ArrayList<>();

    private Mockup(){
        events.add(new EventModel("test1", 2019, 5, 6, 0));
        events.add(new EventModel("test2", 2019, 5, 7, 1));
        events.add(new EventModel("test3", 2019, 5, 8, 2));
        events.add(new EventModel("test4", 2019, 5, 9, 0));
        events.add(new EventModel("test5", 2019, 5, 9, 1));
        events.add(new EventModel("test6", 2019, 5, 9, 2));
    }

    public static Mockup getInstance(){
        return INSTANCE;
    }

    public void saveEvent(EventModel event){
        events.add(event);
    }

    public ArrayList<EventModel> getEvents(Date date){
        return events;
    }
}
