package com.taskplanner.ui;

import com.arellomobile.mvp.MvpView;
import com.taskplanner.EventModel;

import java.util.ArrayList;
import java.util.Calendar;

public interface DayFragmentView extends MvpView {
    public void showEvents(Calendar calendar, ArrayList<EventModel> events);
}
