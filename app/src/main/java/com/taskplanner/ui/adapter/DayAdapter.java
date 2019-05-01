package com.taskplanner.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.taskplanner.presenter.DayFragmentPresenter;
import com.taskplanner.ui.custom_views.DayView;

import java.util.ArrayList;
import java.util.Date;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private DayFragmentPresenter presenter;

    private ArrayList<Date> dates;

    public DayAdapter(DayFragmentPresenter presenter, ArrayList<Date> dates) {
        this.presenter = presenter;
        this.dates = dates;
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
        ((DayView)viewHolder.itemView).setDates(dates.get(i));
        ((DayView)viewHolder.itemView).showEvents(presenter.getEvents(dates.get(i)));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

}
