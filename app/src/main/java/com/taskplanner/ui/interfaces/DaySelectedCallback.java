package com.taskplanner.ui.interfaces;

import android.support.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public interface DaySelectedCallback {
    public void onDateSelected(@NonNull CalendarDay date);
}
