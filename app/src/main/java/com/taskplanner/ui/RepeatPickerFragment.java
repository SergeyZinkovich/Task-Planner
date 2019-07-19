package com.taskplanner.ui;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.presenter.RepeatPickerFragmentPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class RepeatPickerFragment extends MvpAppCompatFragment implements RepeatPickerFragmentView, Spinner.OnItemSelectedListener {

    @BindView(R.id.rbForever) RadioButton rbForever;
    @BindView(R.id.rbCount) RadioButton rbCount;
    @BindView(R.id.rbUntil) RadioButton rbUntil;
    @BindView(R.id.repeatTypeSpinner) Spinner repeatTypeSpinner;
    @BindView(R.id.etRepeatInterval) EditText etRepeatInterval;
    @BindView(R.id.etCount) EditText etCount;
    @BindView(R.id.tvUntilDate) TextView tvUntilDate;
    @BindView(R.id.repeatDaysLabel) TextView repeatDaysLabel;
    @BindView(R.id.llRepeatDays) LinearLayout llRepeatDays;
    @BindView(R.id.cbSunday) CheckBox cbSunday;
    @BindView(R.id.cbMonday) CheckBox cbMonday;
    @BindView(R.id.cbTuesday) CheckBox cbTuesday;
    @BindView(R.id.cbWednesdays) CheckBox cbWednesdays;
    @BindView(R.id.cbThursday) CheckBox cbThursday;
    @BindView(R.id.cbFriday) CheckBox cbFriday;
    @BindView(R.id.cbSaturday) CheckBox cbSaturday;

    @Inject
    Router router;

    @InjectPresenter
    RepeatPickerFragmentPresenter repeatPickerFragmentPresenter;

    @ProvidePresenter
    RepeatPickerFragmentPresenter provideRepeatPickerFragmentPresenter(){
        return new RepeatPickerFragmentPresenter(router);
    }

    private final Long MAX_TIME = 253402300799000L;

    private Calendar endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.repeat_picker_title);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repeate_picker_fragment, container, false);
        ButterKnife.bind(this, view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                new String[]{"DAY", "WEEK", "MONTH", "YEAR"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatTypeSpinner.setAdapter(adapter);
        repeatTypeSpinner.setOnItemSelectedListener(this);

        repeatDaysLabel.setVisibility(View.GONE);
        llRepeatDays.setVisibility(View.GONE);
        etRepeatInterval.setText("1");
        etCount.setText("1");

        Calendar calendar = (Calendar)getArguments().getSerializable("calendar");
        String rrule = getArguments().getString("rrule");
        endDate = calendar;
        if (rrule != null && !rrule.equals("")){
            initViews(calendar, rrule);
        }
        setDateTextView();

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 1){
            repeatDaysLabel.setVisibility(View.VISIBLE);
            llRepeatDays.setVisibility(View.VISIBLE);
        }
        else {
            repeatDaysLabel.setVisibility(View.GONE);
            llRepeatDays.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initViews(Calendar calendar, String rrule){
        rbForever.setChecked(false);
        rbCount.setChecked(false);
        rbUntil.setChecked(false);

        String[] parts = rrule.split(";");
        for (String part : parts){
            if (part.startsWith("FREQ=")) {
                String repeatType = part.substring(5);
                switch (repeatType) {
                    case "DAILY":
                        repeatTypeSpinner.setSelection(0);
                        break;
                    case "WEEKLY":
                        repeatTypeSpinner.setSelection(1);
                        break;
                    case "MONTHLY":
                        repeatTypeSpinner.setSelection(2);
                        break;
                    case "YEARLY":
                        repeatTypeSpinner.setSelection(3);
                        break;
                }
            }
            else if (part.startsWith("INTERVAL=")){
                etRepeatInterval.setText(part.substring(9));
            }
            else if (part.startsWith("BYDAY=")){
                String[] selectedDays = part.substring(6).split(",");
                for (String day : selectedDays){
                    switch (day){
                        case "SU":
                            cbSunday.setChecked(true);
                            break;
                        case "MO":
                            cbMonday.setChecked(true);
                            break;
                        case "TU":
                            cbTuesday.setChecked(true);
                            break;
                        case "WE":
                            cbWednesdays.setChecked(true);
                            break;
                        case "TH":
                            cbSunday.setChecked(true);
                            break;
                        case "FI":
                            cbFriday.setChecked(true);
                            break;
                        case "SA":
                            cbSaturday.setChecked(true);
                            break;
                    }
                }
            }
            else if (part.startsWith("COUNT=")){
                etCount.setText(part.substring(6));
                rbCount.setChecked(true);
                endDate = Calendar.getInstance();
            }
        }
        if (!rbCount.isChecked()){
            if(MAX_TIME == endDate.getTimeInMillis()){
                rbForever.setChecked(true);
                endDate = Calendar.getInstance();
            }
            else {
                rbUntil.setChecked(true);
            }
        }
    }

    public String getRepeatType(){
        switch (repeatTypeSpinner.getSelectedItemPosition()){
            case 0:
                return "DAILY";
            case 1:
                return "WEEKLY";
            case 2:
                return "MONTHLY";
            case 3:
                return "YEARLY";
        }
        return "";
    }

    public String getRepeatInterval(){
        return etRepeatInterval.getText().toString();
    }

    public ArrayList<String> getSelectedDays(){
        ArrayList<String> ans = new ArrayList<>();
        if(cbSunday.isChecked()){
            ans.add("SU");
        }
        if(cbMonday.isChecked()){
            ans.add("MO");
        }
        if(cbTuesday.isChecked()){
            ans.add("TU");
        }
        if(cbWednesdays.isChecked()){
            ans.add("WE");
        }
        if(cbThursday.isChecked()){
            ans.add("TH");
        }
        if(cbFriday.isChecked()){
            ans.add("FR");
        }
        if(cbSaturday.isChecked()){
            ans.add("SA");
        }
        return ans;
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
                repeatPickerFragmentPresenter.setValues(getRepeatType(), getRepeatInterval(),
                        getSelectedDays(), getDurationType(), getRepeatCount(), getEndDate());
                repeatPickerFragmentPresenter.exitWithRrule();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.rbForever)
    public void OnRbForeverClick(){
        rbCount.setChecked(false);
        rbUntil.setChecked(false);
    }

    @OnClick(R.id.rbCount)
    public void OnRbCountClick(){
        rbForever.setChecked(false);
        rbUntil.setChecked(false);
    }

    @OnClick(R.id.rbUntil)
    public void OnRbUntilClick(){
        rbCount.setChecked(false);
        rbForever.setChecked(false);
    }

    public String getDurationType(){
        if(rbForever.isChecked()){
            return "Forever";
        }
        if(rbCount.isChecked()){
            return "Count";
        }
        if(rbUntil.isChecked()){
            return "Until";
        }
        return "";
    }

    public String getRepeatCount(){
        return etCount.getText().toString();
    }

    public Calendar getEndDate(){
        return endDate;
    }

    @OnClick(R.id.tvUntilDate)
    public void OnTvUntilDateClick(){
        new DatePickerDialog(getContext(), dateDialogListener, endDate.get(Calendar.YEAR),
                endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE)).show();
    }

    DatePickerDialog.OnDateSetListener dateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            endDate.set(year, month, dayOfMonth);
            setDateTextView();
        }
    };

    public void setDateTextView(){
        tvUntilDate.setText(SimpleDateFormat.getDateInstance().format(endDate.getTime()));
    }
}
