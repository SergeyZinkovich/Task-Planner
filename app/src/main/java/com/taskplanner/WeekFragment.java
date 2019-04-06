package com.taskplanner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekFragment extends Fragment implements OnDateSelectedListener, CalendarFragmentInterface {

    public interface MonthAndWeekFragmentCallback{
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected);

    }

    MonthAndWeekFragment.MonthAndWeekFragmentCallback monthAndWeekFragmentCallback;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @BindView(R.id.table)
    TableLayout tableLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        monthAndWeekFragmentCallback = (MonthAndWeekFragment.MonthAndWeekFragmentCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_fragment, container, false);
        ButterKnife.bind(this, view);
        calendarView.setDateSelected(new Date(), true);
        //App.getComponent().inject(this);
        calendarView.setOnDateChangedListener(this);

        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        for (int i = 0; i < 24; i++) {
            TableRow t = new TableRow(this.getActivity());
            t.setLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT);
            t.setGravity(Gravity.CENTER);
            for (int j = 0; j < 7; j++) {
                TextView textView = new TextView(this.getActivity());
                textView.setText("test");
                textView.setGravity(Gravity.CENTER);
                t.addView(textView);
            }
            tableLayout.addView(t);
        }

        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        monthAndWeekFragmentCallback.onDateSelected(widget, date, selected);
    }

    @Override
    public Date getDate() {
        return calendarView.getSelectedDate().getDate();
    }
}
