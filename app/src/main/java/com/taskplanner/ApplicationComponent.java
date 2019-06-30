package com.taskplanner;

import com.taskplanner.data.repository.EventPatternRepository;
import com.taskplanner.ui.CreateFragment;
import com.taskplanner.ui.DayFragment;
import com.taskplanner.ui.EventFragment;
import com.taskplanner.ui.MainActivity;
import com.taskplanner.ui.MonthFragment;
import com.taskplanner.ui.SettingsFragment;
import com.taskplanner.ui.WeekFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NavigationModule.class,
        ApiModule.class
})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(CreateFragment createFragment);
    void inject(EventFragment eventFragment);
    void inject(MonthFragment monthFragment);
    void inject(WeekFragment weekFragment);
    void inject(DayFragment dayFragment);
    void inject(SettingsFragment settingsFragment);

    void inject(EventPatternRepository eventPatternRepository);
}