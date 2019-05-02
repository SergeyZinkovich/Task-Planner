package com.taskplanner.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.presenter.DayFragmentPresenter;
import com.taskplanner.ui.adapter.DayAdapter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class DayFragment extends MvpAppCompatFragment implements CalendarFragmentInterface, DayFragmentView{

    LinearLayoutManager layoutManager;

    DayAdapter dayAdapter;

    LinearSnapHelper linearSnapHelper;

    @Inject
    Router router;

    @BindView(R.id.dayList)
    RecyclerView recyclerView;

    @InjectPresenter
    DayFragmentPresenter dayFragmentPresenter;

    @ProvidePresenter
    DayFragmentPresenter provideDayFragmentPresenter(){
        return new DayFragmentPresenter(router, (Calendar) getArguments().getSerializable("calendar"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        dayAdapter = new DayAdapter(dayFragmentPresenter, dayFragmentPresenter.getShowedDates());
        linearSnapHelper = new LinearSnapHelper();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.day_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setAdapter(dayAdapter);
        layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        linearSnapHelper.attachToRecyclerView(recyclerView);

        layoutManager.scrollToPositionWithOffset(1, 0);

        recyclerView.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int selectedItemId = layoutManager.findLastCompletelyVisibleItemPosition();
                if (selectedItemId == 2) {
                    dayScrollForward();
                }
                if (selectedItemId == 0) {
                    dayScrollBackward();
                }
            }
        });
        return view;
    }

    public void dayScrollForward(){
        dayFragmentPresenter.daysInc();
        dayAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(1, 0);
    }

    public void dayScrollBackward(){
        dayFragmentPresenter.daysDec();
        dayAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(1, 0);
    }

    @Override
    public Calendar getCalendar() {
        return dayFragmentPresenter.getSelectedCalendar();
    }
}
