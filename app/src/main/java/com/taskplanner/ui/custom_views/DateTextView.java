package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.taskplanner.EventModel;

import java.util.Calendar;

public class DateTextView extends AppCompatTextView {

    public boolean eventSet = false;

    private Calendar calendar;

    private EventModel eventModel;

    public DateTextView(Context context) {
        super(context);
    }

    public DateTextView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
        eventSet = true;
    }
}
