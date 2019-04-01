package com.taskplanner;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;

public class MainActivity extends AppCompatActivity implements MonthAndWeekFragment.MonthAndWeekFragmentCallback{

    boolean inDB;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    @BindView(R.id.editText)
    EditText editText;

    private Navigator mNavigator = new SupportAppNavigator(this, R.id.fragment) {
        @Override
        protected Intent createActivityIntent(String screenKey, Object data) {
            return null;
        }

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            Fragment fragment = null;
            switch (screenKey) {
                case Screens.SCREEN_DAY_FRAGMENT:
                    fragment = new DayFragment();
                    break;
                case Screens.SCREEN_MONTH_AND_WEEK_FRAGMENT:
                    fragment = new WeekFragment();
                    break;
            }
            return fragment;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        /*calendarView.setOnDateChangedListener(this);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
        calendarView.state().edit().setFirstDayOfWeek(Calendar.MONDAY).setCalendarDisplayMode(CalendarMode.WEEKS).commit();*/
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("events",
                new String[] {"description"},
                "name = ?", new String[] {SimpleDateFormat.getDateInstance().format(date.getDate())}, null, null, null);

        inDB = false;

        if (cursor.moveToFirst()) {
            inDB = true;
            int descriptionColIndex = cursor.getColumnIndex("description");
            editText.setText(cursor.getString(descriptionColIndex));
        }
        else {
            editText.setText("");
        }

        sqLiteDatabase.close();
    }

    @OnClick(R.id.button)
    public void onClick(Button button){
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", editText.getText().toString());
        Date date = ((CalendarFragmentInterface)getSupportFragmentManager().findFragmentById(R.id.fragment)).getDate();
        if (!inDB) {
            contentValues.put("name", SimpleDateFormat.getDateInstance().format(date));
            long rowID = sqLiteDatabase.insert("events", null, contentValues);
            Toast.makeText(getApplicationContext(), SimpleDateFormat.getDateInstance().format(date), Toast.LENGTH_LONG).show();
        }
        else{
            sqLiteDatabase.update("events", contentValues, "name = ?", new String[] {SimpleDateFormat.getDateInstance().format(date)});
        }
        sqLiteDatabase.close();
    }

    @OnClick(R.id.button2)
    public void onClick2(Button button){
        /*calendarView.state().edit().setFirstDayOfWeek(Calendar.MONDAY).setCalendarDisplayMode(CalendarMode.MONTHS).commit();*/
    }

    @OnClick(R.id.button3)
    public void onClick3(Button button){
        router.newRootScreen(Screens.SCREEN_MONTH_AND_WEEK_FRAGMENT);
        /*calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).setFirstDayOfWeek(Calendar.MONDAY).commit();*/
    }

    @OnClick(R.id.button4)
    public void onClick4(Button button){
        router.newRootScreen(Screens.SCREEN_DAY_FRAGMENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(mNavigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }
}
