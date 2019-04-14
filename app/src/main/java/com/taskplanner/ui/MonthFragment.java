package com.taskplanner.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.presenter.MonthFragmentPresenter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;
import com.taskplanner.ui.interfaces.DaySelectedCallback;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class MonthFragment extends MvpAppCompatFragment implements OnDateSelectedListener, CalendarFragmentInterface, MonthFragmentView {

    private Date date;

    @InjectPresenter
    MonthFragmentPresenter monthFragmentPresenter;

    DaySelectedCallback daySelectedCallback;

    @Inject
    Router router;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @Override
    public Date getDate() {
        return calendarView.getSelectedDate().getDate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = (Date) getArguments().getSerializable("date");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        daySelectedCallback = (DaySelectedCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.month_fragment, container, false);
        ButterKnife.bind(this, view);
        App.getComponent().inject(this);
        calendarView.setDateSelected(date, true);
        calendarView.setCurrentDate(date);
        calendarView.setOnDateChangedListener(this);
        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        daySelectedCallback.onDateSelected(date);
    }
}
