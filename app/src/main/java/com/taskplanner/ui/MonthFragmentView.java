package com.taskplanner.ui;

import com.arellomobile.mvp.MvpView;
import com.taskplanner.EventModel;

import java.util.ArrayList;
import java.util.Calendar;

public interface MonthFragmentView extends MvpView {
    void setEvents(Calendar calendar, ArrayList<EventModel> events);
}
