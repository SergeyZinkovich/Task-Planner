package com.taskplanner.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.taskplanner.App;
import com.taskplanner.DBHelper;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.MainActivityPresenter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;
import com.taskplanner.ui.interfaces.DaySelectedCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements DaySelectedCallback, MainActivityView{

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator mNavigator = new SupportAppNavigator(this, R.id.fragment) {
        @Override
        protected Intent createActivityIntent(String screenKey, Object data) {
            return null;
        }

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            Fragment fragment = null;
            Bundle args = new Bundle();
            Date date = (Date)data;
            switch (screenKey) {
                case Screens.SCREEN_DAY_FRAGMENT:
                    fragment = new DayFragment();
                    args.putSerializable("date", date);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_MONTH_AND_WEEK_FRAGMENT:
                    fragment = new MonthFragment();
                    args.putSerializable("date", date);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_WEEK_FRAGMENT:
                    fragment = new WeekFragment();
                    args.putSerializable("date", date);
                    fragment.setArguments(args);
                    break;
            }
            return fragment;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        router.newRootScreen(Screens.SCREEN_MONTH_AND_WEEK_FRAGMENT, new Date());
    }

    @Override
    public void onDateSelected(@NonNull CalendarDay date) {

    }

    @OnClick(R.id.buttonMonth)
    public void onClick2(Button button){
        router.newRootScreen(Screens.SCREEN_MONTH_AND_WEEK_FRAGMENT,
                ((CalendarFragmentInterface)getSupportFragmentManager().findFragmentById(R.id.fragment)).getDate());
    }

    @OnClick(R.id.buttonWeek)
    public void onClick3(Button button){
        router.newRootScreen(Screens.SCREEN_WEEK_FRAGMENT,
                ((CalendarFragmentInterface)getSupportFragmentManager().findFragmentById(R.id.fragment)).getDate());
    }

    @OnClick(R.id.buttonDay)
    public void onClick4(Button button){
        router.newRootScreen(Screens.SCREEN_DAY_FRAGMENT,
                ((CalendarFragmentInterface)getSupportFragmentManager().findFragmentById(R.id.fragment)).getDate());
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(mNavigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }
}
