package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.taskplanner.EventModel;
import com.taskplanner.R;

import java.util.ArrayList;
import java.util.Date;

public class DayView extends ScrollView implements View.OnClickListener {

    private ArrayList<DateTextView> textViews = new ArrayList<>();

    public interface OnDayItemClickListener{
        void onClick(View v);
    }

    OnDayItemClickListener onClickListener;

    public DayView(Context context) {
        super(context);
        this.setLayoutParams(new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LinearLayout verticalLayout = new LinearLayout(context);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 24; i++){
            LinearLayout horizontalLayout = new LinearLayout(context);
            horizontalLayout.setGravity(Gravity.CENTER_VERTICAL);
            TextView textView = new TextView(context);
            if (i < 10) {
                textView.setText("0" + i);
            } else {
                textView.setText(String.valueOf(i));
            }
            DateTextView dateTextView= new DateTextView(context);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params1.setMargins(8, 0, 8,0);
            textView.setLayoutParams(params1);
            dateTextView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 150);
            params.weight = 1.0f;
            dateTextView.setLayoutParams(params);
            dateTextView.setOnClickListener(this);
            textViews.add(dateTextView);
            View border = new View(context);
            border.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
            border.setBackgroundColor(getResources().getColor(R.color.borderColor));
            horizontalLayout.addView(textView);
            horizontalLayout.addView(dateTextView);
            verticalLayout.addView(horizontalLayout);
            verticalLayout.addView(border);
        }
        this.addView(verticalLayout);
    }

    public void setDates(Date date){
        for (int i = 0; i < 24; i++){
            Date date1 = (Date)date.clone();
            date1.setHours(i);
            textViews.get(i).setDate(date1);
        }
    }

    public void showEvents(ArrayList<EventModel> events){
        for (EventModel event: events) {
            textViews.get(event.getHour()).setText(event.getDescription());
        }
    }

    @Override
    public void onClick(View v) {
        onClickListener.onClick(v);
    }

    public void setOnClickListener(OnDayItemClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
