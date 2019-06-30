package com.taskplanner;

import android.app.Application;

import com.taskplanner.data.repository.EventPatternRepository;

public class App extends Application {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.create();
        EventPatternRepository eventPatternRepository = new EventPatternRepository();
        eventPatternRepository.getPatterns();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
