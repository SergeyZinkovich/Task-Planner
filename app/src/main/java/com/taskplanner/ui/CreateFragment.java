package com.taskplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.presenter.CreateActivityPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class CreateFragment extends MvpAppCompatFragment implements CreateActivityView, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Calendar calendar;

    @InjectPresenter
    CreateActivityPresenter createActivityPresenter;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    @BindView(R.id.eventDescription)
    EditText editText;

    @BindView(R.id.dateText)
    TextView dateText;

    @BindView(R.id.timeText)
    TextView timeText;

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
        View view = inflater.inflate(R.layout.create_fragment, container, false);
        ButterKnife.bind(this, view);

        calendar = (Calendar)getArguments().getSerializable("calendar");
        setTextViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                router.exit();
                return true;
            case R.id.actionDone:
                saveEvent();
                router.exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTextViews(){
        dateText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
        timeText.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime())); //TODO: мб разобраться че студия ругается
    }

    @OnClick(R.id.dateText)
    public void onDateClick(TextView textView){
        new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        setTextViews();
    }

    @OnClick(R.id.timeText)
    public void onTimeClick(TextView textView){
        new TimePickerDialog(getContext(), this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        setTextViews();
    }

    public void saveEvent(){
        String description = editText.getText().toString();
        createActivityPresenter.saveEvent(description, calendar);
    }
}
