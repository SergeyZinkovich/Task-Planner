package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.DataEngine;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.ui.CreateFragmentView;

import java.util.Calendar;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class CreateFragmentPresenter extends MvpPresenter<CreateFragmentView>
        implements DataEngine.DeleteEventCallback {

    private Router router;

    public CreateFragmentPresenter(Router router){
        this.router = router;
    }

    public void saveEvent(String description, Calendar calendar){
        EventModel event = new EventModel(description, calendar);
        event.setEndTime(calendar);
        event.setName("");
        DataEngine.getInstance().saveEvent(event, this);
    }

    @Override
    public void deleteEventSuccess(boolean success) {
        if(success) {
            router.showSystemMessage("Successfully saved");
            router.exit();
        }
        else {
            router.showSystemMessage("Save failed");
        }
    }
}
