package com.taskplanner.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.ui.adapter.DayFragmentAdapter;
import com.taskplanner.R;
import com.taskplanner.presenter.DayFragmentPresenter;
import com.taskplanner.ui.adapter.DayHolderAdapter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayFragment extends MvpAppCompatFragment implements CalendarFragmentInterface, DayFragmentView{

    private Date date;

    @InjectPresenter
    DayFragmentPresenter dayFragmentPresenter;

    @BindView(R.id.dayHolder)
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = (Date) getArguments().getSerializable("date");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.day, container, false);
        ButterKnife.bind(this, view);
/*        textView.setText(SimpleDateFormat.getDateInstance().format(date));
        DayFragmentAdapter dayFragmentAdapter = new DayFragmentAdapter();
        recyclerView.setAdapter(dayFragmentAdapter);*/
        ArrayList<Date> dates = new ArrayList<Date>();
        dates.add(date);
        dates.add(date);
        dates.add(date);
        DayHolderAdapter dayHolderAdapter = new DayHolderAdapter(dates);
        recyclerView.setAdapter(dayHolderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    public void setDateText(){

    }

    @Override
    public Date getDate() {
        return date;
    }
}
