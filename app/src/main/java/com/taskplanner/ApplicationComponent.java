package com.taskplanner;

import com.taskplanner.ui.MainActivity;
import com.taskplanner.ui.MonthFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NavigationModule.class
})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(MonthFragment monthFragment);
}