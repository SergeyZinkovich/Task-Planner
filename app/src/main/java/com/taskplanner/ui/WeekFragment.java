package com.taskplanner.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.taskplanner.R;
import com.taskplanner.presenter.WeekFragmentPresenter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;
import com.taskplanner.ui.interfaces.DaySelectedCallback;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekFragment extends MvpAppCompatFragment implements OnDateSelectedListener, CalendarFragmentInterface, WeekFragmentView {

    private Date date;

    @InjectPresenter
    WeekFragmentPresenter weekFragmentPresenter;

    public interface MonthAndWeekFragmentCallback{  //TODO: вынести интерфейс
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected);

    }

    DaySelectedCallback daySelectedCallback;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @BindView(R.id.TimeTable)
    TableLayout tableLayout;

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
        View view = inflater.inflate(R.layout.week_fragment, container, false);
        ButterKnife.bind(this, view);
        calendarView.setDateSelected(date, true);
        calendarView.setCurrentDate(date);
        //App.getComponent().inject(this);
        calendarView.setOnDateChangedListener(this);

        for (int i = 0; i < 24; i++) {
            TableRow t = new TableRow(this.getActivity());
            t.setLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT);
            t.setGravity(Gravity.CENTER);

            TextView textView1 = new TextView(this.getActivity());  //TODO: убрать объявление переменной
            textView1.setBackgroundColor(Color.WHITE);
            TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, //TODO: убрать объявление переменной
                    200);
            params1.setMargins(1,1,1,1);
            textView1.setLayoutParams(params1);
            if(i < 10){
                textView1.setText("0" + String.valueOf(i));
            }
            else {
                textView1.setText(String.valueOf(i));
            }
            textView1.setGravity(Gravity.CENTER);
            t.addView(textView1);

            for (int j = 0; j < 7; j++) {
                TextView textView = new TextView(this.getActivity());
                textView.setBackgroundColor(Color.WHITE);
                TableRow.LayoutParams params = new TableRow.LayoutParams(0,
                        200);
                params.setMargins(1,1,1,1);
                textView.setLayoutParams(params);
                textView.setGravity(Gravity.CENTER);
                t.addView(textView);
            }
            //tableLayout.setColumnStretchable(0, false);
            tableLayout.addView(t);
        }

        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        daySelectedCallback.onDateSelected(date);
    }

    @Override
    public Date getDate() {
        return calendarView.getSelectedDate().getDate();
    }
}
