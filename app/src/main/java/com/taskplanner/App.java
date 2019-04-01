package com.taskplanner;

import android.app.Application;

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
