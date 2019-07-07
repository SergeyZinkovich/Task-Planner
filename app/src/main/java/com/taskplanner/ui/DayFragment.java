package com.taskplanner.ui;

import android.app.DatePickerDialog;
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
import com.taskplanner.App;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.DayFragmentPresenter;
import com.taskplanner.ui.adapter.DayAdapter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class DayFragment extends MvpAppCompatFragment implements CalendarFragmentInterface, DayFragmentView, DatePickerDialog.OnDateSetListener{

    LinearLayoutManager layoutManager;

    DayAdapter dayAdapter;

    LinearSnapHelper linearSnapHelper;

    @Inject
    Router router;

    @BindView(R.id.dayList)
    RecyclerView recyclerView;

    @InjectPresenter
    DayFragmentPresenter dayFragmentPresenter;

    @ProvidePresenter
    DayFragmentPresenter provideDayFragmentPresenter(){
        return new DayFragmentPresenter(router, (Calendar) getArguments().getSerializable("calendar"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        dayAdapter = new DayAdapter(dayFragmentPresenter, dayFragmentPresenter.getShowedDates());
        linearSnapHelper = new LinearSnapHelper();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.day_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setAdapter(dayAdapter);
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
        dayFragmentPresenter.daysInc();
        dayAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(1, 0);
    }

    public void dayScrollBackward(){
        dayFragmentPresenter.daysDec();
        dayAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(1, 0);
    }

    @Override
    public void showEvents(Calendar calendar, ArrayList<EventModel> events) {
        dayAdapter.setEvents(calendar, events);
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
        dayFragmentPresenter.setDays(Calendar.getInstance());
        dayAdapter.notifyDataSetChanged();
    }

    public void selectDate(){
        Calendar c = getCalendar();
        new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        dayFragmentPresenter.setDays(calendar);
        dayAdapter.notifyDataSetChanged();
    }

    @Override
    public Calendar getCalendar() {
        return dayFragmentPresenter.getSelectedCalendar();
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
    public void onClick4(Button button){
        router.navigateTo(Screens.SCREEN_DAY_FRAGMENT, getCalendar());
    }

    @OnClick(R.id.addButton)
    public void addButtonClick(){
        router.navigateTo(Screens.SCREEN_CREATE_FRAGMENT, getCalendar());
    }
}
