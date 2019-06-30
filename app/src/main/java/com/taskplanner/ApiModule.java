package com.taskplanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taskplanner.data.TaskPlannerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    TaskPlannerApi provideTaskPlannerApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://planner.skillmasters.ga/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(TaskPlannerApi.class);
    }
}
