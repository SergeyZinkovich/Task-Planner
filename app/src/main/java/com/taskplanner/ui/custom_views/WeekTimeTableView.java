package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.taskplanner.EventModel;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekTimeTableView extends TableLayout implements View.OnClickListener {

    private ArrayList<ArrayList<EventTextView>> textViews = new ArrayList<>();

    private ArrayList<ArrayList<DateLinearLayout>> layouts = new ArrayList<>();

    private Calendar firstWeekDate;

    public interface OnWeekCellClickListener{
        void onCreateClick(View v);
        void onEventClick(EventTextView v);
    }

    OnWeekCellClickListener onClickListener;

    public WeekTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 24; i++) {
            TableRow t = new TableRow(context);
            t.setLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT);
            t.setGravity(Gravity.CENTER);
            textViews.add(new ArrayList<EventTextView>());
            layouts.add(new ArrayList<DateLinearLayout>());

            for (int j = 0; j < 8; j++) {
                EventTextView textView = new EventTextView(context);
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                if (j == 0){
                    TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            200);
                    params1.setMargins(1,1,1,1);
                    textView.setLayoutParams(params1);
                    if(i < 10){
                        textView.setText("0" + String.valueOf(i));  //TODO: добавить нажатия на время
                    }
                    else {
                        textView.setText(String.valueOf(i));
                    }
                    t.addView(textView);
                }
                else{
                    DateLinearLayout layout = new DateLinearLayout(context);
                    layout.setBackgroundColor(Color.WHITE);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT);
                    params.setMargins(1,1,1,1);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
                    textView.setOnClickListener(this);
                    layout.setOnClickListener(this);
                    layout.setLayoutParams(params);
                    layout.addView(textView);
                    layouts.get(i).add(layout);
                    textViews.get(i).add(textView);
                    t.addView(layout);
                }
            }
            this.addView(t);
        }
    }

    public void setDates(Calendar firstWeekDate){
        this.firstWeekDate = firstWeekDate;
        Calendar calendar1 = (Calendar) firstWeekDate.clone();
        for (int i = 0; i < 24; i++){
            calendar1.set(Calendar.HOUR, i);
            for (int j = 0; j < 7; j++){
                Calendar calendar2 = (Calendar)calendar1.clone();
                calendar2.set(Calendar.DATE, calendar2.get(Calendar.DATE) + j);
                layouts.get(i).get(j).setCalendar(calendar2);
            }
        }
    }

    public void showEvents(ArrayList<EventModel> events){
        for (EventModel event: events) {
            textViews.get(event.getHour()).get(event.getDay() - firstWeekDate.get(Calendar.DATE)).setText(event.getDescription());
            textViews.get(event.getHour()).get(event.getDay() - firstWeekDate.get(Calendar.DATE)).setEventModel(event);
        }
    }

    public void clean(){
        for (ArrayList<EventTextView> arr: textViews){
            for (EventTextView textView: arr){
                textView.setText("");
                textView.setEventModel(null);
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
