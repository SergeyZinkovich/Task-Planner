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

public class DayHolderAdapter extends RecyclerView.Adapter<DayHolderAdapter.ViewHolder> {

    private final ArrayList<Date> dates;

    public DayHolderAdapter(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dateView;
        final RecyclerView recyclerView;

        ViewHolder(View view){
            super(view);
            dateView = (TextView) view.findViewById(R.id.date);
            recyclerView = (RecyclerView) view.findViewById(R.id.list);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.day_fragment, viewGroup, false);
        return new DayHolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.dateView.setText(SimpleDateFormat.getDateInstance().format(dates.get(i)));
        DayFragmentAdapter dayFragmentAdapter = new DayFragmentAdapter();
        viewHolder.recyclerView.setAdapter(dayFragmentAdapter);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

}
