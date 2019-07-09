package com.taskplanner.presenter;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

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

    public void saveEvent(String description, Calendar calendar){
        EventModel event = new EventModel(description, calendar);
        event.setEndTime(calendar);
        event.setName("");
        DataEngine.getInstance().saveEvent(event, this);
    }

    @Override
    public void deleteEventSuccess(boolean success) {
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

    public void updateEvent(String description, Calendar calendar){


        event.setDescription(description);
        event.setEndTime(calendar);
        DataEngine.getInstance().updateEvent(event, this);  //TODO: проверять на наличие изменений
    }

}
