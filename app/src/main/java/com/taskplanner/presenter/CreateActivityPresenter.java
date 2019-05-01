package com.taskplanner.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.ui.CreateActivityView;

import java.util.Date;

public class CreateActivityPresenter extends MvpPresenter<CreateActivityView> {

    public void saveEvent(String description, int year, int month, int day, int hour, int minute){
        Mockup.getInstance().saveEvent(new EventModel(description, year, month, day, hour));
    }
}
