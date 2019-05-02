package com.taskplanner.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.taskplanner.presenter.DayFragmentPresenter;
import com.taskplanner.ui.custom_views.DayView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private DayFragmentPresenter presenter;

    private ArrayList<Calendar> calendars;

    public DayAdapter(DayFragmentPresenter presenter, ArrayList<Calendar> calendars) {
        this.presenter = presenter;
        this.calendars = calendars;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view){
            super(view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DayView view = new DayView(viewGroup.getContext());
        view.setOnClickListener(presenter);
        return new DayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ((DayView)viewHolder.itemView).setDates(calendars.get(i));
        ((DayView)viewHolder.itemView).showEvents(presenter.getEvents(calendars.get(i)));
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

}
