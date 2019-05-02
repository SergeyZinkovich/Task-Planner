package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.Calendar;

public class DateLinearLayout extends LinearLayout {

    private Calendar calendar;

    public DateLinearLayout(Context context) {
        super(context);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

}
