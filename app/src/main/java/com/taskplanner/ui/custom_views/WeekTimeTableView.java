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

import java.util.ArrayList;

public class WeekTimeTableView extends TableLayout implements View.OnClickListener {

    ArrayList<ArrayList<TextView>> textViews = new ArrayList<ArrayList<TextView>>();

    public WeekTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 24; i++) {
            TableRow t = new TableRow(context);
            t.setLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT);
            t.setGravity(Gravity.CENTER);
            textViews.add(new ArrayList<TextView>());

            for (int j = 0; j < 8; j++) {
                TextView textView = new TextView(context);
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


    @Override
    public void onClick(View v) {

    }
}
