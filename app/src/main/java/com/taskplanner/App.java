package com.taskplanner;

import android.app.Application;

import com.taskplanner.data.entity.EventEntity;
import com.taskplanner.data.entity.EventPatternEntity;
import com.taskplanner.data.repository.EventPatternRepository;
import com.taskplanner.data.repository.EventRepository;

public class App extends Application {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.create();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
