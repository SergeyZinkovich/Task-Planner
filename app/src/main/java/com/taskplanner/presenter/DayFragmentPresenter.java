package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.ui.DayFragmentView;

import java.util.ArrayList;
import java.util.Date;

@InjectViewState
public class DayFragmentPresenter extends MvpPresenter<DayFragmentView> {

    private Date selectedDate;

    private ArrayList<Date> showedDates;

    public DayFragmentPresenter(Date date) {
        this.selectedDate = date;
        this.showedDates = new ArrayList<Date>();
        for (int i = 0; i < 3; i++) {
            Date res = (Date)selectedDate.clone();
            res.setDate(res.getDate() + i - 1);
            showedDates.add(res);
        }
    }


    public Date getSelectedDate() {
        return selectedDate;
    }

    public ArrayList<Date> getShowedDates() {
        return showedDates;
    }

    public void daysInc(){
        showedDates.remove(0);
        Date date1 = (Date)showedDates.get(1).clone();
        date1.setDate(date1.getDate() + 1);
        showedDates.add(date1);
    }

    public void daysDec(){
        showedDates.remove(2);
        Date date1 = (Date)showedDates.get(0).clone();
        date1.setDate(date1.getDate() - 1);
        showedDates.add(0, date1);
    }
}
