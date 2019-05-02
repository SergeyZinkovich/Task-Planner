package com.taskplanner.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taskplanner.R;
import com.taskplanner.presenter.WeekFragmentPresenter;
import com.taskplanner.ui.custom_views.WeekTimeTableView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {

    private WeekFragmentPresenter presenter;

    private ArrayList<Calendar> calendars;

    public WeekAdapter(ArrayList<Calendar> calendars, WeekFragmentPresenter presenter){
        this.calendars = calendars;
        this.presenter = presenter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final WeekTimeTableView weekTimeTableView;

        ViewHolder(View view){
            super(view);
            weekTimeTableView = (WeekTimeTableView) view.findViewById(R.id.TimeTable);
        }
    }

    @NonNull
    @Override
    public WeekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.week_time_table_view, viewGroup, false);
        return new WeekAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekAdapter.ViewHolder viewHolder, int i) {
        viewHolder.weekTimeTableView.setOnClickListener(presenter);
        viewHolder.weekTimeTableView.setDates(calendars.get(i));
        viewHolder.weekTimeTableView.showEvents(presenter.getEvents(calendars.get(i)));
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
