package com.taskplanner.ui.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.presenter.DayFragmentPresenter;
import com.taskplanner.ui.custom_views.DateTextView;

import java.util.ArrayList;
import java.util.Date;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private DayFragmentPresenter presenter;

    private Date date;

    private ArrayList<EventModel> events;

    public DayAdapter(DayFragmentPresenter presenter, Date date) {
        this.presenter = presenter;
        this.date = date;

        events = presenter.getEvents(date);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final DateTextView dateTextView;
        final ConstraintLayout constraintLayout;

        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.timeText);
            dateTextView = (DateTextView) view.findViewById(R.id.dateTextView);
            constraintLayout = (ConstraintLayout)view.findViewById(R.id.dayItemLayout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.day_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i < 10) {
            viewHolder.nameView.setText("0" + String.valueOf(i));
        } else {
            viewHolder.nameView.setText(String.valueOf(i));
        }
        Date date1 = (Date)date.clone();
        date1.setHours(i);//TODO: убрать легаси методы
        viewHolder.dateTextView.setDate(date1);
        viewHolder.constraintLayout.setOnClickListener(presenter);
        if ((i < events.size()) && (i >= 0)) {
            viewHolder.dateTextView.setText(events.get(i).getDescription());
        }
        else {
            viewHolder.dateTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return 24;
    }

}
