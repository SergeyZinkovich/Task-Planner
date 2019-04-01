package com.taskplanner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DayFragmentAdapter extends RecyclerView.Adapter<DayFragmentAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.day_fragment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i < 10) {
            viewHolder.nameView.setText("0" + String.valueOf(i));
        } else {
            viewHolder.nameView.setText(String.valueOf(i));
        }
    }

    @Override
    public int getItemCount() {
        return 24;
    }

}
