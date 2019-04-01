package com.taskplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

public class DayFragment extends Fragment implements CalendarFragmentInterface{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.day_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        DayFragmentAdapter dayFragmentAdapter = new DayFragmentAdapter();
        recyclerView.setAdapter(dayFragmentAdapter);
        return view;
    }

    @Override
    public Date getDate() {
        return null;
    }
}
