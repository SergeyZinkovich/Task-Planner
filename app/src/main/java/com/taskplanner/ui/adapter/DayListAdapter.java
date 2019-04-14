package com.taskplanner.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taskplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {

    private ArrayList<Date> dates;

    public DayListAdapter(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dateView;
        final RecyclerView recyclerView;

        ViewHolder(View view){
            super(view);
            dateView = (TextView) view.findViewById(R.id.dateText);
            recyclerView = (RecyclerView) view.findViewById(R.id.day);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.day, viewGroup, false);
        return new DayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.dateView.setText(SimpleDateFormat.getDateInstance().format(dates.get(i)));
        DayAdapter dayAdapter = new DayAdapter();
        viewHolder.recyclerView.setAdapter(dayAdapter);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

}
