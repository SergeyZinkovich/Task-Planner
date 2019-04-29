package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Date;

public class DateTextView extends AppCompatTextView {

    private Date date;

    public DateTextView(Context context) {
        super(context);
    }

    public DateTextView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
