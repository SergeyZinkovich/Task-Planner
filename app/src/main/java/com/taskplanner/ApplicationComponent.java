package com.taskplanner;

import com.taskplanner.data.repository.EventPatternRepository;
import com.taskplanner.data.repository.EventRepository;
import com.taskplanner.data.repository.PermissionRepository;
import com.taskplanner.ui.EditFragment;
import com.taskplanner.ui.DayFragment;
import com.taskplanner.ui.EventFragment;
import com.taskplanner.ui.MainActivity;
import com.taskplanner.ui.MonthFragment;
import com.taskplanner.ui.PermissionFragment;
import com.taskplanner.ui.RepeatPickerFragment;
import com.taskplanner.ui.SettingsFragment;
import com.taskplanner.ui.ShareFragment;
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
    void inject(EditFragment editFragment);
    void inject(EventFragment eventFragment);
    void inject(MonthFragment monthFragment);
    void inject(WeekFragment weekFragment);
    void inject(DayFragment dayFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(RepeatPickerFragment repeatPickerFragment);
    void inject(ShareFragment shareFragment);
    void inject(PermissionFragment permissionFragment);

    void inject(EventPatternRepository eventPatternRepository);
    void inject(EventRepository eventRepository);
    void inject(PermissionRepository permissionRepository);
}