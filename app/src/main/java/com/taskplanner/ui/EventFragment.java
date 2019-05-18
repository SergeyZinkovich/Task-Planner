package com.taskplanner.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.App;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.presenter.EventFragmentPresenter;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class EventFragment extends MvpAppCompatFragment implements EventActivityView {

    @Inject
    Router router;

    @InjectPresenter
    EventFragmentPresenter eventFragmentPresenter;

    @BindView(R.id.dateTextView)
    TextView dateTextView;

    @BindView(R.id.timeTextView)
    TextView timeTextView;

    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment, container, false);
        ButterKnife.bind(this, view);

        EventModel event = (EventModel)getArguments().getParcelable("event");
        dateTextView.setText(SimpleDateFormat.getDateInstance().format(event.getCalendar().getTime()));
        timeTextView.setText(new SimpleDateFormat("HH:mm").format(event.getCalendar().getTime()));
        descriptionTextView.setText(event.getDescription());

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                router.exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
