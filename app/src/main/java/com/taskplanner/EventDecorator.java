package com.taskplanner;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.taskplanner.EventModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private HashSet<Integer> dates;

    public EventDecorator(ArrayList<EventModel> events) {
        setDates(events);
    }

    public void setDates(ArrayList<EventModel> events) {
        this.dates = new HashSet<>();
        for (EventModel event: events){
            dates.add(event.getDay());
        }
    }

    public HashSet<Integer> getDates() {
        return dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day.getCalendar().get(Calendar.DATE));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, Color.BLACK));
    }
}