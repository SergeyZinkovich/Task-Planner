package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.data.DataEngine;
import com.taskplanner.EventModel;
import com.taskplanner.ui.EditFragmentView;

import java.util.Calendar;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class EditFragmentPresenter extends MvpPresenter<EditFragmentView>
        implements DataEngine.RequestEventCallback {

    private Router router;

    private boolean isNew;
    private EventModel event;
    private boolean rruleEnabled = false;
    private String rrule;
    private Calendar rruleEndTime;

    public EditFragmentPresenter(Router router){
        this.router = router;
    }

    public void setNew(EventModel event){
        isNew = true;
        this.event = event;
        rrule = event.getRrule();
        if (rrule != null && !rrule.equals("")){
            rruleEnabled = true;
            getViewState().setRepeatChecked();
        }
        rruleEndTime = event.getEndTime();
    }

    public boolean isNew() {
        return isNew;
    }

    public void setRrule(String rrule, Calendar rruleEndTime){
        rruleEnabled = true;
        this.rrule = rrule;
        this.rruleEndTime = rruleEndTime;
    }

    public void disableRrule(){
        rruleEnabled = false;
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
        if (rruleEnabled){
            event.setRrule(rrule);
            event.setEndTime(rruleEndTime);
        }
        else {
            event.setRrule(null);
            event.setEndTime(endTime);
        }
        DataEngine.getInstance().saveEvent(event, this);
    }

    @Override
    public void requestEventSuccess(boolean success) {
        if(isNew) {
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
        if (rruleEnabled){
            event.setRrule(rrule);
            event.setEndTime(rruleEndTime);
        }
        else {
            event.setEndTime(endTime);
            event.setRrule(null);
        }
        DataEngine.getInstance().updateEvent(event, this);  //TODO: проверять на наличие изменений
    }

}
