package com.taskplanner;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener{

    boolean inDB;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        calendarView.setOnDateChangedListener(this);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
        calendarView.state().edit().setFirstDayOfWeek(Calendar.MONDAY).setCalendarDisplayMode(CalendarMode.WEEKS).commit();
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
        if (!inDB) {
            contentValues.put("name", SimpleDateFormat.getDateInstance().format(calendarView.getSelectedDate().getDate()));
            long rowID = sqLiteDatabase.insert("events", null, contentValues);
            Toast.makeText(getApplicationContext(), SimpleDateFormat.getDateInstance().format(calendarView.getSelectedDate().getDate()), Toast.LENGTH_LONG).show();
        }
        else{
            sqLiteDatabase.update("events", contentValues, "name = ?", new String[] {SimpleDateFormat.getDateInstance().format(calendarView.getSelectedDate().getDate())});
        }
        sqLiteDatabase.close();
    }

    @OnClick(R.id.button2)
    public void onClick2(Button button){
        calendarView.state().edit().setFirstDayOfWeek(Calendar.MONDAY).setCalendarDisplayMode(CalendarMode.MONTHS).commit();
    }

    @OnClick(R.id.button3)
    public void onClick3(Button button){
        calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).setFirstDayOfWeek(Calendar.MONDAY).commit();
    }
}
