package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.data.DataEngine;
import com.taskplanner.ui.MonthFragmentView;

import java.util.ArrayList;
import java.util.Calendar;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MonthFragmentPresenter extends MvpPresenter<MonthFragmentView> implements DataEngine.GetEventCallback {

    private Router router;

    public MonthFragmentPresenter(Router router){
        this.router = router;
    }

    public void getEvents(Calendar calendar){
        Calendar startOfMonth = (Calendar) calendar.clone(); //TODO:  убрать лишние дни, обработчик даблклика
        startOfMonth.set(Calendar.DATE, 1);
        startOfMonth.set(Calendar.HOUR, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.MONTH, endOfMonth.get(Calendar.MONTH) + 1);
        endOfMonth.set(Calendar.SECOND, endOfMonth.get(Calendar.SECOND) - 1);
        DataEngine.getInstance().getEventInstances(startOfMonth, endOfMonth, this);
    }

    @Override
    public void setEvents(Calendar calendar, ArrayList<EventModel> events) {
        getViewState().setEvents(calendar, events);
    }

    @Override
    public void getEventsFailed() {
        router.showSystemMessage("Get event filed");
    }
}
