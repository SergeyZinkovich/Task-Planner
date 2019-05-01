package com.taskplanner.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.Screens;
import com.taskplanner.ui.DayFragmentView;
import com.taskplanner.ui.custom_views.DateTextView;
import com.taskplanner.ui.custom_views.DayView;

import java.util.ArrayList;
import java.util.Date;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class DayFragmentPresenter extends MvpPresenter<DayFragmentView> implements DayView.OnDayItemClickListener {

    private Router router;

    private Date selectedDate;

    private ArrayList<Date> showedDates;

    public DayFragmentPresenter(Router router, Date date) {
        this.router = router;
        this.selectedDate = date;
        this.showedDates = new ArrayList<Date>();
        for (int i = 0; i < 3; i++) {
            Date res = (Date)selectedDate.clone();
            res.setDate(res.getDate() + i - 1); //TODO: remove legacy methods
            showedDates.add(res);
        }
    }


    public Date getSelectedDate() {
        return selectedDate; //TODO: исправить, возвращает неверный день
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

    public ArrayList<EventModel> getEvents(Date date){
        return Mockup.getInstance().getEvents(date);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof DateTextView){
            router.navigateTo(Screens.SCREEN_WEEK_FRAGMENT, ((DateTextView) v).getDate());
        }
    }
}
