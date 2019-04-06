package com.taskplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class MonthAndWeekFragment extends Fragment implements OnDateSelectedListener, CalendarFragmentInterface {

    public interface MonthAndWeekFragmentCallback{
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected);

    }

    MonthAndWeekFragmentCallback monthAndWeekFragmentCallback;

    @Inject
    Router router;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @Override
    public Date getDate() {
        return calendarView.getSelectedDate().getDate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        monthAndWeekFragmentCallback = (MonthAndWeekFragmentCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.month_and_week_fragment, container, false);
        ButterKnife.bind(this, view);
        App.getComponent().inject(this);
        calendarView.setDateSelected(new Date(), true);
        calendarView.setOnDateChangedListener(this);
        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        monthAndWeekFragmentCallback.onDateSelected(widget, date, selected);
    }
}
