package com.taskplanner.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.Screens;
import com.taskplanner.ui.DayFragmentView;
import com.taskplanner.ui.WeekFragmentView;
import com.taskplanner.ui.custom_views.DateTextView;
import com.taskplanner.ui.custom_views.WeekTimeTableView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class WeekFragmentPresenter extends MvpPresenter<WeekFragmentView> implements WeekTimeTableView.OnWeekCellClickListener {

    private Router router;

    private ArrayList<Date> showedDates = new ArrayList<>();

    public WeekFragmentPresenter(Router router, Date date){
        this.router = router;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Date date1 = (Date)date.clone();
        date1.setDate(date1.getDate() - dayOfWeek + 1);
        Date date2 = (Date) date1.clone();
        date2.setDate(date2.getDate() - 7);
        Date date3 = (Date) date1.clone();
        date3.setDate(date3.getDate() + 7);
        showedDates.add(date2);
        showedDates.add(date1);
        showedDates.add(date3);
    }

    public void daysInc() {
        for (Date date: showedDates) {
            date.setDate(date.getDate() + 7);
        }
    }

    public void daysDec() {
        for (Date date: showedDates) {
            date.setDate(date.getDate() - 7);
        }
    }

    public ArrayList<EventModel> getEvents(Date firstDateOfWeek){
        return Mockup.getInstance().getEvents(firstDateOfWeek);
    }

    public ArrayList<Date> getShowedDates(){
        return showedDates;
    }

    @Override
    public void onClick(View v) {
        if(v instanceof DateTextView) {
            router.navigateTo(Screens.SCREEN_DAY_FRAGMENT, ((DateTextView) v).getDate());
        }
    }
}
