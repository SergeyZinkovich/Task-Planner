package com.taskplanner.presenter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.DBHelper;
import com.taskplanner.ui.MainActivity;
import com.taskplanner.ui.MainActivityView;

import java.text.SimpleDateFormat;
import java.util.Date;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {

    public void daySelected(Date date){
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

}
