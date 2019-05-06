package com.taskplanner.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.presenter.EventFragmentPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends MvpAppCompatActivity implements EventActivityView {

    @BindView(R.id.dateTextView)
    TextView dateTextView;

    @BindView(R.id.timeTextView)
    TextView timeTextView;

    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;

    @InjectPresenter
    EventFragmentPresenter eventFragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        EventModel event = (EventModel)getIntent().getParcelableExtra("event");
/*        dateTextView.setText(SimpleDateFormat.getDateInstance().format(
                new Date(event.getYear(), event.getMonth(), event.getDay(), event.getHour(), 0)));*/
        dateTextView.setText(SimpleDateFormat.getDateInstance().format(event.getCalendar()));
        timeTextView.setText(event.getHour() + ":00");
        descriptionTextView.setText(event.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
