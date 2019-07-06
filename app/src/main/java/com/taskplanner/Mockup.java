package com.taskplanner;

import java.util.ArrayList;
import java.util.Calendar;

public class Mockup {

    private static final Mockup INSTANCE = new Mockup();

    private ArrayList<EventModel> events = new ArrayList<>();

    private Mockup(){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2019, 4, 6, 0, 0);
        events.add(new EventModel("test1", calendar1));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2019, 4, 7, 1, 0);
        events.add(new EventModel("test2", calendar2));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2019, 4, 8, 2, 0);
        events.add(new EventModel("test3", calendar3));
        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(2019, 4, 9, 0, 0);
        events.add(new EventModel("test4", calendar4));
        Calendar calendar5 = Calendar.getInstance();
        calendar5.set(2019, 4, 9, 1, 0);
        events.add(new EventModel("test5", calendar5));
        Calendar calendar6 = Calendar.getInstance();
        calendar6.set(2019, 4, 9, 2, 0);
        events.add(new EventModel("test6", calendar6));
        Calendar calendar7 = Calendar.getInstance();
        calendar7.set(2019, 4, 9, 2, 1);
        events.add(new EventModel("test7", calendar7));
    }

    public static Mockup getInstance(){
        return INSTANCE;
    }

    public void saveEvent(EventModel event){
        events.add(event);
    }

    public ArrayList<EventModel> getDayEvents(Calendar calendar){
        ArrayList<EventModel> result = new ArrayList<>();
        for (EventModel eventModel: events){
            if ((calendar.get(Calendar.DATE) == eventModel.getStartTime().get(Calendar.DATE)) &&
                    (calendar.get(Calendar.MONTH) == eventModel.getStartTime().get(Calendar.MONTH)) &&
                    (calendar.get(Calendar.YEAR) == eventModel.getStartTime().get(Calendar.YEAR))){
                result.add(eventModel);
            }
        }
        return result;
    }

    public ArrayList<EventModel> getWeekEvents(Calendar calendar){
        ArrayList<EventModel> result = new ArrayList<>();
        for (EventModel eventModel: events){
            if ((calendar.get(Calendar.WEEK_OF_MONTH) == eventModel.getStartTime().get(Calendar.WEEK_OF_MONTH)) &&
                    (calendar.get(Calendar.MONTH) == eventModel.getStartTime().get(Calendar.MONTH)) &&
                    (calendar.get(Calendar.YEAR) == eventModel.getStartTime().get(Calendar.YEAR))){
                result.add(eventModel);
            }
        }
        return result;
    }

    public ArrayList<EventModel> getMonthEvents(Calendar calendar){
        ArrayList<EventModel> result = new ArrayList<>();
        for (EventModel eventModel: events){
            if ((calendar.get(Calendar.MONTH) == eventModel.getStartTime().get(Calendar.MONTH)) &&
                    (calendar.get(Calendar.YEAR) == eventModel.getStartTime().get(Calendar.YEAR))){
                result.add(eventModel);
            }
        }
        return result;
    }
}
