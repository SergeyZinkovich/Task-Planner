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

    private ArrayList<Calendar> showedCalendars = new ArrayList<>();

    public WeekFragmentPresenter(Router router, Calendar calendar){
        this.router = router;

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Calendar calendar1 = (Calendar) calendar.clone();
        calendar1.set(Calendar.DATE, calendar1.get(Calendar.DATE) - dayOfWeek + 1);
        Calendar calendar2 = (Calendar) calendar1.clone();
        calendar2.set(Calendar.DATE, calendar2.get(Calendar.DATE) - 7);
        Calendar calendar3 = (Calendar) calendar1.clone();
        calendar3.set(Calendar.DATE, calendar3.get(Calendar.DATE) + 7);
        showedCalendars.add(calendar2);
        showedCalendars.add(calendar1);
        showedCalendars.add(calendar3);
    }

    public void daysInc() {
        for (Calendar calendar: showedCalendars) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 7);
        }
    }

    public void daysDec() {
        for (Calendar calendar: showedCalendars) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
        }
    }

    public ArrayList<EventModel> getEvents(Calendar firstDateOfWeek){
        return Mockup.getInstance().getEvents(firstDateOfWeek);
    }

    public ArrayList<Calendar> getShowedDates(){
        return showedCalendars;
    }

    @Override
    public void onClick(View v) {
        if (((DateTextView) v).eventSet) {
            router.navigateTo(Screens.SCREEN_EVENT_ACTIVITY, ((DateTextView) v).getEventModel());
        } else {
            router.navigateTo(Screens.SCREEN_CREATE_ACTIVITY, ((DateTextView) v).getCalendar());
        }
    }
}
