package com.taskplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.App;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.CreateActivityPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class CreateActivity extends MvpAppCompatActivity implements CreateActivityView {

    @InjectPresenter
    CreateActivityPresenter createActivityPresenter;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    @BindView(R.id.timePicker)
    TimePicker timePicker;

    @BindView(R.id.datePicker)
    DatePicker datePicker;

    @BindView(R.id.eventDescription)
    EditText editText;

    private Navigator mNavigator = new SupportAppNavigator(this, R.id.fragment) {
        @Override
        protected Intent createActivityIntent(String screenKey, Object data) {
            Intent intent = null;
            switch (screenKey){
                case Screens.SCREEN_MAIN_ACTIVITY:
                    intent = new Intent(CreateActivity.this, MainActivity.class);
                    break;
            }
            return intent;
        }

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        App.getComponent().inject(this);

        timePicker.setIs24HourView(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Date date = (Date)getIntent().getSerializableExtra("date");
        timePicker.setCurrentHour(date.getHours()); // TODO: использовать нормальные методы
        timePicker.setCurrentMinute(date.getMinutes());  //TODO:Минуты наверно не надо передавать
        datePicker.updateDate(date.getYear() + 1900, date.getMonth(), date.getDate());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.actionDone:
                saveEvent();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveEvent(){
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String description = editText.getText().toString();

        createActivityPresenter.saveEvent(description, year, month, day, hour, minute);
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator();
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }*/
}
