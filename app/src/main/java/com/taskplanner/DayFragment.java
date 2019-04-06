package com.taskplanner;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayFragment extends Fragment implements CalendarFragmentInterface{

    private Date date;

    @BindView(R.id.date)
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = (Date) getArguments().getSerializable("date");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.day_fragment, container, false);
        ButterKnife.bind(this, view);
        textView.setText(SimpleDateFormat.getDateInstance().format(date));
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
