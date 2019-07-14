package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.taskplanner.EventModel;
import com.taskplanner.R;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekTimeTableView extends TableLayout implements View.OnClickListener {

    private ArrayList<ArrayList<EventTextView>> textViews = new ArrayList<>();

    private ArrayList<ArrayList<DateLinearLayout>> layouts = new ArrayList<>();

    private Calendar firstWeekDate, lastWeekDate;

    public interface OnWeekCellClickListener{
        void onCreateClick(View v);
        void onEventClick(EventTextView v);
    }

    OnWeekCellClickListener onClickListener;

    public WeekTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 24; i++) {
            TableRow t = new TableRow(context);
            t.setLayoutMode(LinearLayout.LayoutParams.WRAP_CONTENT);
            t.setGravity(Gravity.CENTER);
            textViews.add(new ArrayList<EventTextView>());
            layouts.add(new ArrayList<DateLinearLayout>());

            for (int j = 0; j < 8; j++) {
                if (j == 0){
                    EventTextView textView = new EventTextView(context);
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT);
                    params1.setMargins(1,1,1,1);
                    textView.setLayoutParams(params1);
                    if(i < 10){
                        textView.setText("0" + String.valueOf(i));
                    }
                    else {
                        textView.setText(String.valueOf(i));
                    }
                    t.addView(textView);
                }
                else{
                    DateLinearLayout layout = new DateLinearLayout(context);
                    layout.setBackgroundColor(Color.WHITE);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setMinimumHeight(150);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
                    params.setMargins(1,1,1,1);
                    layout.setOnClickListener(this);
                    layout.setLayoutParams(params);
                    layouts.get(i).add(layout);
                    t.addView(layout);
                }
            }
            this.addView(t);
        }
    }

    public void setDates(Calendar firstWeekDate){
        this.firstWeekDate = (Calendar) firstWeekDate.clone();
        lastWeekDate = (Calendar) firstWeekDate.clone();
        lastWeekDate.set(Calendar.DATE, lastWeekDate.get(Calendar.DATE) + 7);
        lastWeekDate.setTimeInMillis(lastWeekDate.getTimeInMillis() - 1);
        Calendar calendar1 = (Calendar) firstWeekDate.clone();
        for (int i = 0; i < 24; i++){
            calendar1.set(Calendar.HOUR_OF_DAY, i);
            for (int j = 0; j < 7; j++){
                Calendar calendar2 = (Calendar)calendar1.clone();
                calendar2.set(Calendar.DATE, calendar2.get(Calendar.DATE) + j);
                layouts.get(i).get(j).setCalendar(calendar2);
            }
        }
    }

    public Calendar getFirstWeekDate(){
        return firstWeekDate;
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
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 150));
        textView.setOnClickListener(this);
        textView.setText(event.getName());
        textView.setEventModel(event);
        int k = event.getDay() - firstWeekDate.get(Calendar.DATE);
        if((event.getStartTime().before(firstWeekDate)) || (event.getStartTime().after(lastWeekDate))){
            return;
        }
        layouts.get(event.getHour()).get(k).addView(textView);
    }

    public void clean(){
        for (ArrayList<DateLinearLayout> arr: layouts){
            for (DateLinearLayout layout: arr){
                layout.removeAllViews();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof EventTextView) {
            onClickListener.onEventClick((EventTextView) v);
        }
        else{
            onClickListener.onCreateClick(v);
        }
    }

    public void setOnClickListener(OnWeekCellClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
