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
import java.util.Calendar;
import java.util.Date;

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
        return selectedDate; //TODO: исправить, возвращает неверный день
    }

    public ArrayList<Calendar> getShowedDates() {
        return showedCalendars;
    }

    public void daysInc(){
        showedCalendars.remove(0);
        Calendar calendar = (Calendar) showedCalendars.get(1).clone();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        showedCalendars.add(calendar);
    }

    public void daysDec(){
        showedCalendars.remove(2);
        Calendar calendar = (Calendar) showedCalendars.get(0).clone();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        showedCalendars.add(0, calendar);
    }

    public ArrayList<EventModel> getEvents(Calendar calendar){
        return Mockup.getInstance().getEvents(calendar);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof DateTextView) {
            if (((DateTextView) v).eventSet) {
                router.navigateTo(Screens.SCREEN_EVENT_ACTIVITY, ((DateTextView) v).getEventModel());
            } else {
                router.navigateTo(Screens.SCREEN_CREATE_ACTIVITY, ((DateTextView) v).getCalendar());
            }
        }
    }
}
