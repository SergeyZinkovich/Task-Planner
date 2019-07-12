package com.taskplanner.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.taskplanner.App;
import com.taskplanner.EventModel;
import com.taskplanner.R;
import com.taskplanner.presenter.RepeatPickerFragmentPresenter;
import com.taskplanner.ui.interfaces.RepeatPickerFragmentView;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class RepeatPickerFragment extends MvpAppCompatFragment implements RepeatPickerFragmentView,
        RadioGroup.OnCheckedChangeListener {

    @Inject
    Router router;

    @InjectPresenter
    RepeatPickerFragmentPresenter repeatPickerFragmentPresenter;

    private String repeatType = "NEVER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.repeat_picker_title);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repeate_picker_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                router.exit();
                return true;
            case R.id.actionDone:
                router.exitWithResult(1, repeatType);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbNever:
                repeatType = "NEVER";
                break;
            case R.id.rbDaily:
                repeatType = "DAILY";
                break;
            case R.id.rbWeekly:
                repeatType = "WEEKLY";
                break;
            case R.id.rbMonthly:
                repeatType = "MONTHLY";
                break;
            case R.id.rbYearly:
                repeatType = "YEARLY";
                break;
            default:
        }
    }
}
