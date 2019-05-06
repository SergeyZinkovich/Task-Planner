package com.taskplanner.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.ui.CreateActivityView;

import java.util.Calendar;
import java.util.Date;

public class CreateActivityPresenter extends MvpPresenter<CreateActivityView> {

    public void saveEvent(String description, int year, int month, int day, int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        Mockup.getInstance().saveEvent(new EventModel(description, calendar));
    }
}
