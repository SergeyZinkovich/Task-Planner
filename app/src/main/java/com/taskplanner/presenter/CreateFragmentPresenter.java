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

    private boolean editMode;
    private EventModel event;
    private String rrule;
    private Calendar rruleEndTime;

    public CreateFragmentPresenter(Router router){
        this.router = router;
    }

    public void setEditMode(EventModel event){
        editMode = true;
        this.event = event;
        rrule = event.getRrule();
        rruleEndTime = event.getEndTime();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setRrule(String rrule, Calendar rruleEndTime){
        this.rrule = rrule;
        this.rruleEndTime = rruleEndTime;
    }

    public String getRrule(){
        return rrule;
    }

    public Calendar getRruleEndTime(){
        return rruleEndTime;
    }

    public void saveEvent(String name, String description, Calendar startTime, Calendar endTime){
        EventModel event = new EventModel(description, startTime);
        event.setDurationFromEndTime(endTime);
        event.setName(name);
        if (rrule != null){
            event.setRrule(rrule);
            event.setEndTime(rruleEndTime);
        }
        else {
            event.setEndTime(endTime);
        }
        DataEngine.getInstance().saveEvent(event, this);
    }

    @Override
    public void requestEventSuccess(boolean success) {
        if(editMode) {
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
        event.setStartTime(startTime);
        event.setRruleStartTimeInMillis(event.getRruleStartTimeInMillis() -
                event.getStartTimeInMillis() + startTime.getTimeInMillis());
        event.setDurationFromEndTime(endTime);
        if (rrule != null){
            event.setRrule(rrule);
            event.setEndTime(rruleEndTime);
        }
        else {
            event.setEndTime(endTime);
        }
        DataEngine.getInstance().updateEvent(event, this);  //TODO: проверять на наличие изменений
    }

}
