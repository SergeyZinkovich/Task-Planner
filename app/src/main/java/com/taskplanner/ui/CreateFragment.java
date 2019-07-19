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
import android.widget.CheckBox;
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
import com.taskplanner.Screens;
import com.taskplanner.presenter.CreateFragmentPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.result.ResultListener;

public class CreateFragment extends MvpAppCompatFragment implements CreateFragmentView, ResultListener {

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
    @BindView(R.id.tvRepeat)
    TextView tvRepeat;
    @BindView(R.id.cbRepeat)
    CheckBox cbRepeat;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.app_name);
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.create_fragment, container, false);
        ButterKnife.bind(this, view);

        startTime = (Calendar)getArguments().getSerializable("calendar");
        if(startTime != null){
            endTime = (Calendar)startTime.clone();
        }
        else {
            EventModel event = (EventModel) getArguments().getParcelable("event");
            createFragmentPresenter.setEditMode(event);
            startTime = event.getStartTime();
            endTime = Calendar.getInstance();
            endTime.setTimeInMillis(startTime.getTimeInMillis() + event.getDuration());
            setNameAndDescription(event);
        }
        setCalendarsTextVies();
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

    public void setCalendarsTextVies(){
        startDateText.setText(SimpleDateFormat.getDateInstance().format(startTime.getTime()));
        startTimeText.setText(new SimpleDateFormat("HH:mm").format(startTime.getTime()));
        endDateText.setText(SimpleDateFormat.getDateInstance().format(endTime.getTime()));
        endTimeText.setText(new SimpleDateFormat("HH:mm").format(endTime.getTime()));
    }

    public void setNameAndDescription(EventModel event){
        etEventDescription.setText(event.getDescription());
        etEventName.setText(event.getName());
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
            setCalendarsTextVies();
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
            setCalendarsTextVies();
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
            setCalendarsTextVies();
        }
    };

    @OnClick(R.id.endTimeText)
    public void onEndTimeClick(TextView textView){
        new TimePickerDialog(getContext(), endTimeDialogListener, endTime.get(Calendar.HOUR),
                endTime.get(Calendar.MINUTE), true).show();
    }

    TimePickerDialog.OnTimeSetListener endTimeDialogListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute);
            setCalendarsTextVies();
        }
    };

    @OnClick(R.id.cbRepeat)
    public void onRepeatCheckBoxRepeatClick(){
        if (cbRepeat.isChecked()){
            cbRepeat.setChecked(false);
            onRepeatTextViewClick();
        }
        else {
            createFragmentPresenter.disableRrule();
        }
    }

    @OnClick(R.id.tvRepeat)
    public void onRepeatTextViewClick(){
        router.setResultListener(1,this);
        Bundle bundle = new Bundle();
        bundle.putString("rrule", createFragmentPresenter.getRrule());
        if (createFragmentPresenter.getRruleEndTime() != null) {
            bundle.putSerializable("calendar", createFragmentPresenter.getRruleEndTime());
        }
        else {
            bundle.putSerializable("calendar", endTime);
        }
        router.navigateTo(Screens.SCREEN_REPEAT_PICKER_FRAGMENT, bundle);
    }

    @Override
    public void onResult(Object resultData) {
        cbRepeat.setChecked(true);
        Bundle bundle = (Bundle) resultData;
        Calendar rruleEndTime = (Calendar) bundle.getSerializable("calendar");
        String rrule = bundle.getString("rrule");
        createFragmentPresenter.setRrule(rrule, rruleEndTime);
    }

    public void doneButtonClick(){
        String name = etEventName.getText().toString();
        String description = etEventDescription.getText().toString();
        if(createFragmentPresenter.isEditMode()) {
            createFragmentPresenter.updateEvent(name, description, startTime, endTime);
        }
        else {
            createFragmentPresenter.saveEvent(name, description, startTime, endTime);
        }
    }

    @Override
    public void setRepeatChecked() {
        cbRepeat.setChecked(true);
    }
}
