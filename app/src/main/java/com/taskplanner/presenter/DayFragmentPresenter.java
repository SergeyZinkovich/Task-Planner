package com.taskplanner.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.Mockup;
import com.taskplanner.Screens;
import com.taskplanner.ui.DayFragmentView;
import com.taskplanner.ui.custom_views.DateLinearLayout;
import com.taskplanner.ui.custom_views.EventTextView;
import com.taskplanner.ui.custom_views.DayView;

import java.util.ArrayList;
import java.util.Calendar;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class DayFragmentPresenter extends MvpPresenter<DayFragmentView> implements DayView.OnDayItemClickListener {

    private Router router;

    private Calendar selectedDate;

    private ArrayList<Calendar> showedCalendars;

    public DayFragmentPresenter(Router router, Calendar calendar) {
        this.router = router;
        this.selectedDate = calendar;
        this.showedCalendars = new ArrayList<Calendar>();
        for (int i = 0; i < 3; i++) {
            Calendar res = (Calendar)selectedDate.clone();
            res.set(Calendar.DATE, res.get(Calendar.DATE) + i - 1);
            showedCalendars.add(res);
        }
    }


    public Calendar getSelectedCalendar() {
        return selectedDate;
    }

    public ArrayList<Calendar> getShowedDates() {
        return showedCalendars;
    }

    public void daysInc(){
        showedCalendars.remove(0);
        Calendar calendar = (Calendar) showedCalendars.get(1).clone();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        showedCalendars.add(calendar);
        selectedDate.set(Calendar.DATE, selectedDate.get(Calendar.DATE) + 1);
    }

    public void daysDec(){
        showedCalendars.remove(2);
        Calendar calendar = (Calendar) showedCalendars.get(0).clone();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        showedCalendars.add(0, calendar);
        selectedDate.set(Calendar.DATE, selectedDate.get(Calendar.DATE) - 1);
    }

    public void setDays(Calendar middleDay){
        showedCalendars.clear();
        for (int i = 0; i < 3; i++) {
            Calendar res = (Calendar)middleDay.clone();
            res.set(Calendar.DATE, res.get(Calendar.DATE) + i - 1);
            showedCalendars.add(res);
        }
    }


    public ArrayList<EventModel> getEvents(Calendar calendar){
        return Mockup.getInstance().getDayEvents(calendar);
    }

    @Override
    public void onCreateClick(View v) {
        router.navigateTo(Screens.SCREEN_CREATE_FRAGMENT, ((DateLinearLayout) v).getCalendar());
    }

    @Override
    public void onEventClick(EventTextView v) {
        router.navigateTo(Screens.SCREEN_EVENT_FRAGMENT,  v.getEventModel());
    }
}
