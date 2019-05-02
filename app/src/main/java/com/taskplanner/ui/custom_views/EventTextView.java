package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.taskplanner.EventModel;

import java.util.Calendar;

public class EventTextView extends AppCompatTextView {

    private EventModel eventModel;

    public EventTextView(Context context) {
        super(context);
    }

    public EventTextView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }
}
