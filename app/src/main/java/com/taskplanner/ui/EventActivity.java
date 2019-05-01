package com.taskplanner.ui;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.presenter.EventFragmentPresenter;

public class EventActivity extends MvpAppCompatActivity implements EventActivityView {

    @InjectPresenter
    EventFragmentPresenter eventFragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
