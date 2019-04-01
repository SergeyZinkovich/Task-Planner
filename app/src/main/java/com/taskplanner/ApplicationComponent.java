package com.taskplanner;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NavigationModule.class
})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(MonthAndWeekFragment monthAndWeekFragment);
}