package com.taskplanner;

import java.util.ArrayList;
import java.util.Date;

public class Mockup {

    private static final Mockup INSTANCE = new Mockup();

    private Mockup(){}

    public static Mockup getInstance(){
        return INSTANCE;
    }

    public ArrayList<EventModel> getEvents(Date date){
        ArrayList<EventModel> result = new ArrayList<>();
        if ((date.getDate() == 5) && (date.getMonth() == 4)) {
            result.add(new EventModel("test1", 2019, 5, 6, 0));
            result.add(new EventModel("test2", 2019, 5, 7, 1));
            result.add(new EventModel("test3", 2019, 5, 8, 2));
            result.add(new EventModel("test4", 2019, 5, 9, 0));
            result.add(new EventModel("test5", 2019, 5, 9, 1));
            result.add(new EventModel("test6", 2019, 5, 9, 2));
        }
        if ((date.getDate() == 9) && (date.getMonth() == 4)) {
            result.add(new EventModel("test4", 2019, 5, 9, 0));
            result.add(new EventModel("test5", 2019, 5, 9, 1));
            result.add(new EventModel("test6", 2019, 5, 9, 2));
        }
        return result;
    }
}
