package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.taskplanner.EventModel;
import com.taskplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DayView extends ScrollView implements View.OnClickListener {

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
            DateLinearLayout horizontalLayout = new DateLinearLayout(context);
            horizontalLayout.setGravity(Gravity.CENTER_VERTICAL);
            horizontalLayout.setOnClickListener(this);
            layouts.add(horizontalLayout);
            TextView textView = new TextView(context);  //TODO: повесить обработчик нажатий на время
            if (i < 10) {
                textView.setText("0" + i);
            } else {
                textView.setText(String.valueOf(i));
            }
            EventTextView eventTextView = new EventTextView(context);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params1.setMargins(8, 0, 8,0);
            textView.setLayoutParams(params1);
            eventTextView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 150);
            params.weight = 1.0f;
            eventTextView.setLayoutParams(params);
            eventTextView.setOnClickListener(this);
            textViews.add(eventTextView);
            View border = new View(context);
            border.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
            border.setBackgroundColor(getResources().getColor(R.color.borderColor));
            horizontalLayout.addView(textView);
            horizontalLayout.addView(eventTextView);
            verticalLayout.addView(horizontalLayout);
            verticalLayout.addView(border);
        }
        this.addView(verticalLayout);
    }

    public void setDates(Calendar calendar){
        dateText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
        for (int i = 0; i < 24; i++){
            Calendar calendar1 = (Calendar)calendar.clone();
            calendar1.set(Calendar.HOUR, i);
            layouts.get(i).setCalendar(calendar1);
        }
    }

    public void showEvents(ArrayList<EventModel> events){
        for (EventModel event: events) {
            textViews.get(event.getHour()).setText(event.getDescription());
            textViews.get(event.getHour()).setEventModel(event);
        }
    }

    public void clean(){
        for (EventTextView textView: textViews){
            textView.setText("");
            textView.setEventModel(null);
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
