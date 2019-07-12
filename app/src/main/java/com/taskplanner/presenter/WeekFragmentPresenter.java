package com.taskplanner.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.DataEngine;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.Screens;
import com.taskplanner.ui.WeekFragmentView;
import com.taskplanner.ui.custom_views.DateLinearLayout;
import com.taskplanner.ui.custom_views.EventTextView;
import com.taskplanner.ui.custom_views.WeekTimeTableView;

import java.util.ArrayList;
import java.util.Calendar;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class WeekFragmentPresenter extends MvpPresenter<WeekFragmentView>
        implements WeekTimeTableView.OnWeekCellClickListener, DataEngine.GetEventCallback{

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

    public void setDays(Calendar middleDay){
        showedCalendars.clear();
        int dayOfWeek = middleDay.get(Calendar.DAY_OF_WEEK);
        Calendar calendar1 = (Calendar) middleDay.clone();
        calendar1.set(Calendar.DATE, calendar1.get(Calendar.DATE) - dayOfWeek + 1);
        Calendar calendar2 = (Calendar) calendar1.clone();
        calendar2.set(Calendar.DATE, calendar2.get(Calendar.DATE) - 7);
        Calendar calendar3 = (Calendar) calendar1.clone();
        calendar3.set(Calendar.DATE, calendar3.get(Calendar.DATE) + 7);
        showedCalendars.add(calendar2);
        showedCalendars.add(calendar1);
        showedCalendars.add(calendar3);
    }

    public void getEvents(Calendar firstDateOfWeek){
        Calendar lastDateOfWeek = (Calendar) firstDateOfWeek.clone();
        lastDateOfWeek.set(Calendar.DATE, lastDateOfWeek.get(Calendar.DATE) + 6);
        lastDateOfWeek.set(Calendar.HOUR, 23);
        lastDateOfWeek.set(Calendar.MINUTE, 59);
        lastDateOfWeek.set(Calendar.SECOND, 59);
        lastDateOfWeek.set(Calendar.MILLISECOND, 999);
        DataEngine.getInstance().getEventInstances(firstDateOfWeek, lastDateOfWeek, this);
    }

    @Override
    public void setEvents(Calendar calendar, ArrayList<EventModel> events){
        getViewState().showEvents(calendar, events);
    }

    public ArrayList<Calendar> getShowedDates(){
        return showedCalendars;
    }

    @Override
    public void onCreateClick(View v) {
        router.navigateTo(Screens.SCREEN_CREATE_FRAGMENT, ((DateLinearLayout) v).getCalendar());
    }

    @Override
    public void onEventClick(EventTextView v) {
        router.navigateTo(Screens.SCREEN_EVENT_FRAGMENT, v.getEventModel());
    }
}
