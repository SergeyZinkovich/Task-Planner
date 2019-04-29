package com.taskplanner.ui.custom_views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.taskplanner.EventModel;

import java.util.ArrayList;
import java.util.Date;

public class WeekTimeTableView extends TableLayout implements View.OnClickListener {

    private ArrayList<ArrayList<DateTextView>> textViews = new ArrayList<>();

    private Date firstWeekDate;

    public interface OnWeekCellClickListener{
        void onClick(View v);
    }

    OnWeekCellClickListener onClickListener;

    public WeekTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 24; i++) {
            TableRow t = new TableRow(context);
            t.setLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT);
            t.setGravity(Gravity.CENTER);
            textViews.add(new ArrayList<DateTextView>());

            for (int j = 0; j < 8; j++) {
                DateTextView textView = new DateTextView(context);
                textView.setBackgroundColor(Color.WHITE);
                if (j == 0){
                    TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            200);
                    params1.setMargins(1,1,1,1);
                    textView.setLayoutParams(params1);
                    if(i < 10){
                        textView.setText("0" + String.valueOf(i));
                    }
                    else {
                        textView.setText(String.valueOf(i));
                    }
                }
                else{
                    TableRow.LayoutParams params = new TableRow.LayoutParams(0, 200);
                    params.setMargins(1,1,1,1);
                    textView.setOnClickListener(this);
                    textView.setLayoutParams(params);
                    textViews.get(i).add(textView);
                }
                textView.setGravity(Gravity.CENTER);
                t.addView(textView);
            }
            this.addView(t);
        }
    }

    public void setDates(Date firstWeekDate){
        this.firstWeekDate = firstWeekDate;
        Date date1 = (Date) firstWeekDate.clone();
        for (int i = 0; i < 24; i++){
            date1.setHours(i);
            for (int j = 0; j < 7; j++){
                Date date2 = (Date)date1.clone();
                date2.setDate(date2.getDate() + j);
                textViews.get(i).get(j).setDate(date2);
            }
        }
    }

    public void showEvents(ArrayList<EventModel> events){
        for (EventModel event: events) {
            textViews.get(event.getHour()).get(event.getDay() - firstWeekDate.getDate()).setText(event.getDescription());
        }
    }


    @Override
    public void onClick(View v) {
        onClickListener.onClick(v);
    }

    public void setOnClickListener(OnWeekCellClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
