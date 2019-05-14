package com.taskplanner.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class MonthFragment extends MvpAppCompatFragment implements CalendarFragmentInterface, MonthFragmentView {

    @InjectPresenter
    MonthFragmentPresenter monthFragmentPresenter;

    @Inject
    Router router;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public Calendar getCalendar() {
        return calendarView.getSelectedDate().getCalendar();
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

}
