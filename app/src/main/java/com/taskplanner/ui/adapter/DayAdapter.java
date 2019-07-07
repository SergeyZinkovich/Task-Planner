package com.taskplanner.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.taskplanner.EventModel;
import com.taskplanner.presenter.DayFragmentPresenter;
import com.taskplanner.ui.custom_views.DayView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private DayFragmentPresenter presenter;
    private ArrayList<DayView> views = new ArrayList<>();
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
        views.add(view);
        return new DayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ((DayView)viewHolder.itemView).clean();
        ((DayView)viewHolder.itemView).setDates(calendars.get(i));
        presenter.getEvents(calendars.get(i));
    }

    public void setEvents(Calendar calendar, ArrayList<EventModel> events){
        for (DayView view: views) {
            if(view.getDate().equals(calendar)){
                view.showEvents(events);
            }

        }
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

}
