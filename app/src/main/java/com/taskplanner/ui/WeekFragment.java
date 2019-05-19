package com.taskplanner.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
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
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.WeekFragmentPresenter;
import com.taskplanner.ui.adapter.WeekAdapter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class WeekFragment extends MvpAppCompatFragment implements OnMonthChangedListener, CalendarFragmentInterface, WeekFragmentView, DatePickerDialog.OnDateSetListener {

    private Calendar previousDay;

    private boolean scrolledProgrammatically = false;

    LinearLayoutManager layoutManager;

    WeekAdapter weekAdapter;

    LinearSnapHelper linearSnapHelper;

    @Inject
    Router router;

    @InjectPresenter
    WeekFragmentPresenter weekFragmentPresenter;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @BindView(R.id.timeTableList)
    RecyclerView recyclerView;

    @ProvidePresenter
    WeekFragmentPresenter provideDayFragmentPresenter(){
        return new WeekFragmentPresenter(router, (Calendar) getArguments().getSerializable("calendar"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        weekAdapter = new WeekAdapter(weekFragmentPresenter.getShowedDates(), weekFragmentPresenter);
        linearSnapHelper = new LinearSnapHelper();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Calendar calendar = (Calendar) getArguments().getSerializable("calendar");
        View view = inflater.inflate(R.layout.week_fragment, container, false);
        ButterKnife.bind(this, view);
        calendarView.setDateSelected(calendar, true);
        calendarView.setCurrentDate(calendar);
        previousDay = (Calendar)calendar.clone();
        calendarView.setOnMonthChangedListener(this);


        recyclerView.setAdapter(weekAdapter);
        layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        linearSnapHelper.attachToRecyclerView(recyclerView);

        layoutManager.scrollToPositionWithOffset(1, 0);

        recyclerView.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int selectedItemId = layoutManager.findLastCompletelyVisibleItemPosition();
                if (selectedItemId == 2) {
                    dayScrollForward();
                }
                if (selectedItemId == 0) {
                    dayScrollBackward();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_day_mover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        scrolledProgrammatically = true;
        calendarView.setCurrentDate(Calendar.getInstance());
        calendarView.setSelectedDate(Calendar.getInstance());
        weekFragmentPresenter.setDays(Calendar.getInstance());
        weekAdapter.notifyDataSetChanged();
        scrolledProgrammatically = false;
    }

    public void selectDate(){
        Calendar c = getCalendar();
        new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        scrolledProgrammatically = true;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        calendarView.setSelectedDate(calendar);
        calendarView.setCurrentDate(calendar);
        weekFragmentPresenter.setDays(calendar);
        weekAdapter.notifyDataSetChanged();
        scrolledProgrammatically = false;
    }

    public void dayScrollForward(){
        if (!scrolledProgrammatically) {
            calendarView.goToNext();
        }
        scrolledProgrammatically = false;
        weekFragmentPresenter.daysInc();
        weekAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(1, 0);
    }

    public void dayScrollBackward(){
        if (!scrolledProgrammatically) {
            calendarView.goToPrevious();
        }
        scrolledProgrammatically = false;
        weekFragmentPresenter.daysDec();
        weekAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(1, 0);
    }

    @Override
    public Calendar getCalendar() {
        return calendarView.getSelectedDate().getCalendar();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        if (!scrolledProgrammatically) {
            if (date.getCalendar().after(previousDay)) {
                recyclerView.smoothScrollToPosition(2);
            } else if (date.getCalendar().before(previousDay)) {
                recyclerView.smoothScrollToPosition(0);
            }
        }
        previousDay = date.getCalendar();
        scrolledProgrammatically = true;
    }

    @OnClick(R.id.buttonMonth)
    public void onClick2(Button button){
        router.navigateTo(Screens.SCREEN_MONTH_FRAGMENT, getCalendar());
    }

    @OnClick(R.id.buttonWeek)
    public void onClick3(Button button){
        router.navigateTo(Screens.SCREEN_WEEK_FRAGMENT, getCalendar());
    }

    @OnClick(R.id.buttonDay)
    public void onClick4(Button button) {
        router.navigateTo(Screens.SCREEN_DAY_FRAGMENT, getCalendar());
    }

    @OnClick(R.id.addButton)
    public void addButtonClick(){
        router.navigateTo(Screens.SCREEN_CREATE_FRAGMENT, getCalendar());
    }

}
