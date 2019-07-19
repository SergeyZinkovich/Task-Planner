package com.taskplanner.presenter;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.ui.RepeatPickerFragmentView;

import java.util.ArrayList;
import java.util.Calendar;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class RepeatPickerFragmentPresenter extends MvpPresenter<RepeatPickerFragmentView> {

    private final Long MAX_TIME = 253402300799000L;

    private Router router;
    private String repeatType;
    private String repeatInterval;
    private ArrayList<String> selectedDays;
    private String durationType;
    private String repeatCount;
    private Calendar endDate;

    public RepeatPickerFragmentPresenter(Router router){
        this.router = router;
    }

    public void setValues(String repeatType, String repeatInterval, ArrayList<String> selectedDays,
                          String durationType, String repeatCount, Calendar endDate){
        this.repeatType = repeatType;
        this.repeatInterval = repeatInterval;
        this.selectedDays = selectedDays;
        this.durationType = durationType;
        this.repeatCount = repeatCount;
        this.endDate = endDate;
    }

    public void exitWithRrule(){
        StringBuilder rrule = new StringBuilder("FREQ=");
        rrule.append(repeatType).append(";");
        String repeatInterval = getRepeatInterval();
        if (repeatInterval == null){
            router.showSystemMessage("Enter repeat interval");
            return;
        }
        rrule.append("INTERVAL=").append(repeatInterval);
        if(repeatType.equals("WEEKLY")){
            if (selectedDays.size() != 0) {
                rrule.append(";BYDAY=");
                rrule.append(selectedDays.get(0));
                for (int i = 1; i < selectedDays.size(); i++) {
                    rrule.append(",").append(selectedDays.get(i));
                }
            }
            else {
                router.showSystemMessage("Select repeat days");
                return;
            }
        }
        Calendar calendar = Calendar.getInstance();
        switch (durationType){
            case "Forever":
                calendar.setTimeInMillis(MAX_TIME);
                break;
            case "Count":
                String repeatCount = getRepeatCount();
                if (repeatCount == null){
                    router.showSystemMessage("Enter repeat count number");
                    return;
                }
                rrule.append(";COUNT=").append(repeatCount);
                calendar.setTimeInMillis(MAX_TIME);
                break;
            case "Until":
                calendar = endDate;
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putString("rrule", rrule.toString());
        bundle.putSerializable("calendar", calendar);
        router.exitWithResult(1, bundle);
    }

    private String getRepeatInterval(){
        String s = repeatInterval;
        if (!s.equals("") && !s.equals("0") && !s.equals("00")){
            if(s.startsWith("0")){
                return s.substring(1);
            }
            else {
                return s;
            }
        }
        else {
            return null;
        }
    }

    private String getRepeatCount(){
        String s = repeatCount;
        if (!s.equals("") && !s.equals("0") && !s.equals("00")){
            if(s.startsWith("0")){
                return s.substring(1);
            }
            else {
                return s;
            }
        }
        else {
            return null;
        }
    }
}
