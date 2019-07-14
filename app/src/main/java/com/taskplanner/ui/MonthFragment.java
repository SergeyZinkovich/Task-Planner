package com.taskplanner.ui;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.MonthFragmentPresenter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class MonthFragment extends MvpAppCompatFragment implements CalendarFragmentInterface, MonthFragmentView, DatePickerDialog.OnDateSetListener {

    @InjectPresenter
    MonthFragmentPresenter monthFragmentPresenter;

    @Inject
    Router router;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Calendar calendar = (Calendar) getArguments().getSerializable("calendar");
        View view = inflater.inflate(R.layout.month_fragment, container, false);
        ButterKnife.bind(this, view);
        App.getComponent().inject(this);
        calendarView.setDateSelected(calendar, true);
        calendarView.setCurrentDate(calendar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_day_mover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
}
