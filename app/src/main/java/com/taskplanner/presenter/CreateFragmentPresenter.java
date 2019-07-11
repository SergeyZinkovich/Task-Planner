package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.DataEngine;
import com.taskplanner.EventModel;
import com.taskplanner.ui.CreateFragmentView;

import java.util.Calendar;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class CreateFragmentPresenter extends MvpPresenter<CreateFragmentView>
        implements DataEngine.RequestEventCallback {

    private Router router;

    private boolean updateMode;

    private EventModel event;
    public CreateFragmentPresenter(Router router){
        this.router = router;
    }

    public void setUpdateMode(EventModel event){
        updateMode = true;
        this.event = event;
    }

    public boolean isUpdateMode() {
        return updateMode;
    }

    public void saveEvent(String name, String description, Calendar startTime, Calendar entTime){
        EventModel event = new EventModel(description, startTime);
        event.setEndTime(startTime);
        event.setEndTime(entTime);
        event.setName(name);
        DataEngine.getInstance().saveEvent(event, this);
    }

    @Override
    public void requestEventSuccess(boolean success) {
        if(updateMode) {
            if (success) {
                router.showSystemMessage("Successfully updated");
                router.exit();
            } else {
                router.showSystemMessage("Update failed");
            }
        }
        else {
            if (success) {
                router.showSystemMessage("Successfully saved");
                router.exit();
            } else {
                router.showSystemMessage("Save failed");
            }
        }
    }

    public void updateEvent(String name, String description, Calendar startTime, Calendar endTime){
        event.setName(name);
        event.setDescription(description);
        event.setEndTime(startTime);
        event.setEndTime(endTime);
        DataEngine.getInstance().updateEvent(event, this);  //TODO: проверять на наличие изменений
    }

}
