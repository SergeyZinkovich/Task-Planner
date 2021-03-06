package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.Resource;
import com.taskplanner.EventModel;
import com.taskplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DayView extends ScrollView implements View.OnClickListener {

    private Calendar date;
    private ArrayList<EventTextView> textViews = new ArrayList<>();
    private ArrayList<DateLinearLayout> layouts = new ArrayList<>();
    private TextView dateText;

    public interface OnDayItemClickListener{
        void onCreateClick(View v);
        void onEventClick(EventTextView v);
    }

    OnDayItemClickListener onClickListener;

    public DayView(Context context) {
        super(context);
        this.setLayoutParams(new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LinearLayout verticalLayout = new LinearLayout(context);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        dateText = new TextView(context);
        dateText.setGravity(Gravity.CENTER);
        dateText.setTextSize(20);
        verticalLayout.addView(dateText);
        for (int i = 0; i < 24; i++){

            LinearLayout horizontalLayout = new LinearLayout(context);
            horizontalLayout.setGravity(Gravity.CENTER_VERTICAL);

            TextView textView = new TextView(context);
            if (i < 10) {
                textView.setText("0" + i);
            } else {
                textView.setText(String.valueOf(i));
            }
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params1.setMargins(8, 0, 8,0);
            textView.setLayoutParams(params1);

            DateLinearLayout eventslayout = new DateLinearLayout(context);
            eventslayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            eventslayout.setLayoutParams(params);
            eventslayout.setMinimumHeight(150);
            eventslayout.setOnClickListener(this);
            layouts.add(eventslayout);

            View border = new View(context);
            border.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
            border.setBackgroundColor(getContext().getColor(R.color.borderColor));

            horizontalLayout.addView(textView);
            horizontalLayout.addView(eventslayout);
            verticalLayout.addView(horizontalLayout);
            verticalLayout.addView(border);
        }
        this.addView(verticalLayout);
    }

    public void setDates(Calendar calendar){
        date = (Calendar) calendar.clone();
        dateText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
        for (int i = 0; i < 24; i++){
            Calendar calendar1 = (Calendar)calendar.clone();
            calendar1.set(Calendar.HOUR_OF_DAY, i);
            layouts.get(i).setCalendar(calendar1);
        }
    }

    public Calendar getDate(){
        return date;
    }

    public void showEvents(ArrayList<EventModel> events){
        for (EventModel event: events) {
            addEvent(event);
        }
    }

    public void addEvent(EventModel event){
        EventTextView textView = new EventTextView(this.getContext());
        textView.setBackground(getContext().getDrawable(R.drawable.event_background));
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
        textView.setText(event.getName());
        textView.setEventModel(event);
        textView.setOnClickListener(this);
        if(event.getDay() != date.get(Calendar.DATE)){
            return;
        }
        layouts.get(event.getHour()).addView(textView);
    }

    public void clean(){
        for (DateLinearLayout layout: layouts){
            layout.removeAllViews();
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof EventTextView){
            onClickListener.onEventClick((EventTextView) v);
        }
        else {
            onClickListener.onCreateClick(v);
        }
    }

    public void setOnClickListener(OnDayItemClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
