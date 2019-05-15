package com.taskplanner.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.App;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.MainActivityPresenter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView{

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator mNavigator = new SupportAppNavigator(this, R.id.fragment) {
        @Override
        protected Intent createActivityIntent(String screenKey, Object data) {
            Intent intent = null;
            switch (screenKey){
                case Screens.SCREEN_EVENT_FRAGMENT:
                    EventModel eventModel = (EventModel)data;
                    intent = new Intent(MainActivity.this, EventFragment.class);
                    intent.putExtra("event", eventModel);
                    break;
            }
            return intent;
        }

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            Fragment fragment = null;
            Bundle args = new Bundle();
            Calendar calendar = (Calendar) data;
            switch (screenKey) {
                case Screens.SCREEN_DAY_FRAGMENT:
                    fragment = new DayFragment();
                    args.putSerializable("calendar", calendar);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_MONTH_FRAGMENT:
                    fragment = new MonthFragment();
                    args.putSerializable("calendar", calendar);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_WEEK_FRAGMENT:
                    fragment = new WeekFragment();
                    args.putSerializable("calendar", calendar);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_CREATE_FRAGMENT:
                    fragment = new CreateFragment();
                    args.putSerializable("calendar", calendar);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_EVENT_FRAGMENT:
                    fragment = new CreateFragment();
                    args.putSerializable("calendar", calendar);
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
        router.newRootScreen(Screens.SCREEN_MONTH_FRAGMENT, Calendar.getInstance());
    }

    @OnClick(R.id.addButton)
    public void addButtonClick(){
        router.navigateTo(Screens.SCREEN_CREATE_FRAGMENT,
                ((CalendarFragmentInterface)getSupportFragmentManager().findFragmentById(R.id.fragment)).getCalendar());
    }

    @Override
    public void onBackPressed() {
        router.exit();
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
