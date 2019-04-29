package com.taskplanner;

import com.taskplanner.ui.CreateActivity;
import com.taskplanner.ui.DayFragment;
import com.taskplanner.ui.MainActivity;
import com.taskplanner.ui.MonthFragment;
import com.taskplanner.ui.WeekFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NavigationModule.class
})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(CreateActivity createActivity);
    void inject(MonthFragment monthFragment);
    void inject(WeekFragment weekFragment);
    void inject(DayFragment dayFragment);
}