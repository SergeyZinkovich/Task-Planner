package com.taskplanner.ui;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpView;
import com.taskplanner.EventModel;

import java.util.ArrayList;
import java.util.Calendar;

public interface WeekFragmentView extends MvpView {
    void showEvents(Calendar calendar, ArrayList<EventModel> events);
    void setSelectedDate();
}
