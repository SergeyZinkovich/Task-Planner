package com.taskplanner.ui;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taskplanner.App;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.Screens;
import com.taskplanner.presenter.MainActivityPresenter;
import com.taskplanner.ui.interfaces.CalendarFragmentInterface;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView{

    private static int RC_SIGN_IN = 9001;

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
            Calendar calendar;
            switch (screenKey) {
                case Screens.SCREEN_DAY_FRAGMENT:
                    calendar = (Calendar) data;
                    fragment = new DayFragment();
                    args.putSerializable("calendar", calendar);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_MONTH_FRAGMENT:
                    calendar = (Calendar) data;
                    fragment = new MonthFragment();
                    args.putSerializable("calendar", calendar);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_WEEK_FRAGMENT:
                    calendar = (Calendar) data;
                    fragment = new WeekFragment();
                    args.putSerializable("calendar", calendar);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_CREATE_FRAGMENT:
                    fragment = new CreateFragment();
                    if(data instanceof Calendar){
                        calendar = (Calendar) data;
                        args.putSerializable("calendar", calendar);
                    }
                    else {
                        EventModel event = (EventModel) data;
                        args.putParcelable("event", event);
                    }
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_EVENT_FRAGMENT:
                    EventModel event = (EventModel) data;
                    fragment = new EventFragment();
                    args.putParcelable("event", event);
                    fragment.setArguments(args);
                    break;
                case Screens.SCREEN_SETTINGS_FRAGMENT:
                    fragment = new SettingsFragment();
                    break;
                case Screens.SCREEN_REPEAT_PICKER_FRAGMENT:
                    Bundle bundle = (Bundle) data;
                    fragment = new RepeatPickerFragment();
                    fragment.setArguments(bundle);
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
        initUser();
        router.newRootScreen(Screens.SCREEN_MONTH_FRAGMENT, Calendar.getInstance());
    }

    public void initUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());



            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            //.setLogo(R.drawable.my_great_logo) //TODO: добавить пикчу
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        }
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
