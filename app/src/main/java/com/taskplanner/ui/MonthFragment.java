package com.taskplanner.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.taskplanner.App;
import com.taskplanner.EventDecorator;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.MonthFragmentPresenter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class MonthFragment extends MvpAppCompatFragment implements CalendarFragmentInterface,
        MonthFragmentView, DatePickerDialog.OnDateSetListener, OnDateSelectedListener, OnMonthChangedListener {

    @Inject
    Router router;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @InjectPresenter
    MonthFragmentPresenter monthFragmentPresenter;

    @ProvidePresenter
    MonthFragmentPresenter provideMonthFragmentPresenter(){
        return new MonthFragmentPresenter(router);
    }

    private EventDecorator eventDecorator = new EventDecorator(new ArrayList<>());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Calendar calendar = (Calendar) getArguments().getSerializable("calendar");
        View view = inflater.inflate(R.layout.month_fragment, container, false);
        ButterKnife.bind(this, view);
        calendarView.setDateSelected(calendar, true);
        calendarView.setCurrentDate(calendar);
        calendarView.addDecorator(eventDecorator);
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);
        monthFragmentPresenter.getEvents(calendarView.getCurrentDate().getCalendar());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_day_mover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                router.navigateTo(Screens.SCREEN_SHARE_FRAGMENT, null);
                return true;
            case R.id.settings:
                router.navigateTo(Screens.SCREEN_SETTINGS_FRAGMENT);
                return true;
            case R.id.goToCurrentDate:
                goToCurrentDate();
                return true;
            case R.id.selectDate:
                selectDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setEvents(Calendar calendar, ArrayList<EventModel> events) {
            calendarView.removeDecorator(eventDecorator);
            eventDecorator.setDates(events);
            calendarView.addDecorator(eventDecorator);
    }

    public void goToCurrentDate(){
        calendarView.setCurrentDate(Calendar.getInstance());
        calendarView.setSelectedDate(Calendar.getInstance());
    }

    public void selectDate(){
        Calendar c = getCalendar();
        new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        calendarView.setSelectedDate(calendar);
        calendarView.setCurrentDate(calendar);
    }

    @Override
    public Calendar getCalendar() {
        return calendarView.getSelectedDate().getCalendar();
    }

    @OnClick(R.id.buttonMonth)
    public void onMonthButtonClick(Button button){
        router.navigateTo(Screens.SCREEN_MONTH_FRAGMENT, getCalendar());
    }

    @OnClick(R.id.buttonWeek)
    public void onWeekButtonClick(Button button){
        router.navigateTo(Screens.SCREEN_WEEK_FRAGMENT, getCalendar());
    }

    @OnClick(R.id.buttonDay)
    public void onDayButtonClick(Button button) {
        router.navigateTo(Screens.SCREEN_DAY_FRAGMENT, getCalendar());
    }

    @OnClick(R.id.addButton)
    public void addButtonClick(){
        router.navigateTo(Screens.SCREEN_CREATE_FRAGMENT, getCalendar());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        router.navigateTo(Screens.SCREEN_DAY_FRAGMENT, date.getCalendar());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        monthFragmentPresenter.getEvents(calendarView.getCurrentDate().getCalendar());
    }
}
