package com.taskplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.taskplanner.App;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.presenter.CreateFragmentPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class CreateFragment extends MvpAppCompatFragment implements CreateFragmentView {

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    @BindView(R.id.eventName)
    EditText etEventName;
    @BindView(R.id.eventDescription)
    EditText etEventDescription;  //TODO: переименовать все view
    @BindView(R.id.startDateText)
    TextView startDateText;
    @BindView(R.id.startTimeText)
    TextView startTimeText;
    @BindView(R.id.endDateText)
    TextView endDateText;
    @BindView(R.id.endTimeText)
    TextView endTimeText;

    @InjectPresenter
    CreateFragmentPresenter createFragmentPresenter;

    @ProvidePresenter
    CreateFragmentPresenter provideCreateFragmentPresenter(){
        return new CreateFragmentPresenter(router);
    }

    private Calendar startTime;
    private Calendar endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);

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

        startTime = (Calendar)getArguments().getSerializable("calendar");
        if(startTime != null){
            endTime = (Calendar)startTime.clone();
            setTextViews(startTime, endTime);
        }
        else {
            EventModel event = (EventModel) getArguments().getParcelable("event");
            createFragmentPresenter.setUpdateMode(event);
            startTime = event.getStartTime();
            setTextViews(event);
        }
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
                doneButtonClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTextViews(Calendar startTime, Calendar endTime){
        startDateText.setText(SimpleDateFormat.getDateInstance().format(startTime.getTime()));
        startTimeText.setText(new SimpleDateFormat("HH:mm").format(startTime.getTime())); //TODO: мб разобраться че студия ругается
        endDateText.setText(SimpleDateFormat.getDateInstance().format(endTime.getTime()));
        endTimeText.setText(new SimpleDateFormat("HH:mm").format(endTime.getTime()));
    }

    public void setTextViews(EventModel event){
        startDateText.setText(SimpleDateFormat.getDateInstance().format(event.getStartTime().getTime()));
        startTimeText.setText(new SimpleDateFormat("HH:mm").format(event.getStartTime().getTime()));
        endDateText.setText(SimpleDateFormat.getDateInstance().format(event.getEndTime().getTime()));
        endTimeText.setText(new SimpleDateFormat("HH:mm").format(event.getEndTime().getTime()));
        etEventDescription.setText(event.getDescription());
    }

    @OnClick(R.id.startDateText)
    public void onStartDateClick(TextView textView){
        new DatePickerDialog(getContext(), startDateDialogListener, startTime.get(Calendar.YEAR),
                startTime.get(Calendar.MONTH), startTime.get(Calendar.DATE)).show();
    }

    DatePickerDialog.OnDateSetListener startDateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            startTime.set(year, month, dayOfMonth);
            setTextViews(startTime, endTime);
        }
    };

    @OnClick(R.id.startTimeText)
    public void onStartTimeClick(TextView textView){
        new TimePickerDialog(getContext(), startTimeDialogListener, startTime.get(Calendar.HOUR),
                startTime.get(Calendar.MINUTE), true).show();
    }

    TimePickerDialog.OnTimeSetListener startTimeDialogListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startTime.set(Calendar.MINUTE, minute);
            setTextViews(startTime, endTime);
        }
    };

    @OnClick(R.id.endDateText)
    public void onEndDateClick(TextView textView){
        new DatePickerDialog(getContext(), endDateDialogListener, endTime.get(Calendar.YEAR),
                endTime.get(Calendar.MONTH), endTime.get(Calendar.DATE)).show();
    }

    DatePickerDialog.OnDateSetListener endDateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            endTime.set(year, month, dayOfMonth);
            setTextViews(startTime, endTime);
        }
    };

    @OnClick(R.id.startTimeText)
    public void onEndTimeClick(TextView textView){
        new TimePickerDialog(getContext(), endTimeDialogListener, endTime.get(Calendar.HOUR),
                endTime.get(Calendar.MINUTE), true).show();
    }

    TimePickerDialog.OnTimeSetListener endTimeDialogListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute);
            setTextViews(startTime, endTime);
        }
    };

    public void doneButtonClick(){
        String name = etEventName.getText().toString();
        String description = etEventDescription.getText().toString();
        if(createFragmentPresenter.isUpdateMode()) {
            createFragmentPresenter.updateEvent(name, description, startTime, endTime);
        }
        else {
            createFragmentPresenter.saveEvent(name, description, startTime, endTime);
        }
    }
}
