package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.ui.CreateFragmentView;

import java.util.Calendar;

@InjectViewState
public class CreateFragmentPresenter extends MvpPresenter<CreateFragmentView> {

    public void saveEvent(String description, Calendar calendar){
        Mockup.getInstance().saveEvent(new EventModel(description, calendar));
    }
}
