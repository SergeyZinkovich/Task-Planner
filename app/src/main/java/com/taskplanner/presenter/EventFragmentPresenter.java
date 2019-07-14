package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.DataEngine;
import com.taskplanner.EventModel;
import com.taskplanner.Screens;
import com.taskplanner.ui.EventActivityView;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class EventFragmentPresenter extends MvpPresenter<EventActivityView>
        implements DataEngine.RequestEventCallback {

    private Router router;
    private EventModel event;

    public EventFragmentPresenter(Router router, EventModel event){
        this.router = router;
        this.event = event;
    }

    public void editEvent(){
        router.navigateTo(Screens.SCREEN_CREATE_FRAGMENT, event);
    }

    public void deleteEvent(){
        getViewState().setDeleteInProgress(true);
        DataEngine.getInstance().deleteEvent(event, this);
    }

    @Override
    public void requestEventSuccess(boolean success) {
        getViewState().setDeleteInProgress(false);
        router.exit();
    }

    public EventModel getEvent() {
        return event;
    }
}
