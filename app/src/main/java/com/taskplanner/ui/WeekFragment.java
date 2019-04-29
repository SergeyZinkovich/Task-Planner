package com.taskplanner.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.presenter.WeekFragmentPresenter;
import com.taskplanner.ui.adapter.DayListAdapter;
import com.taskplanner.ui.adapter.WeekAdapter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;
import com.taskplanner.ui.interfaces.DaySelectedCallback;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class WeekFragment extends MvpAppCompatFragment implements OnDateSelectedListener, OnMonthChangedListener,  CalendarFragmentInterface, WeekFragmentView, View.OnClickListener {

    private Date previousDay;

    private boolean scrolledProgrammatically = false;

    DaySelectedCallback daySelectedCallback;  //TODO: колбек наверн не нужен

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
        return new WeekFragmentPresenter(router, (Date) getArguments().getSerializable("date"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        weekAdapter = new WeekAdapter(weekFragmentPresenter.getShowedDates(), weekFragmentPresenter);
        linearSnapHelper = new LinearSnapHelper();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        daySelectedCallback = (DaySelectedCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable("date");
        View view = inflater.inflate(R.layout.week_fragment, container, false);
        ButterKnife.bind(this, view);
        calendarView.setDateSelected(date, true);
        calendarView.setCurrentDate(date);
        previousDay = (Date)date.clone();
        calendarView.setOnDateChangedListener(this);
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
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        daySelectedCallback.onDateSelected(date);
    }

    @Override
    public Date getDate() {
        return calendarView.getSelectedDate().getDate();
    }

    @Override
    public void onClick(View v) {
        notifyAll();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        if(date.getDate().after(previousDay)){
            recyclerView.smoothScrollToPosition(2);
        }
        else if(date.getDate().before(previousDay)){
            recyclerView.smoothScrollToPosition(0);
        }
        previousDay = date.getDate();
        scrolledProgrammatically = true;
    }
}
