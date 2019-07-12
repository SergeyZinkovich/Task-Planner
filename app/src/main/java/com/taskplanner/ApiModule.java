package com.taskplanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taskplanner.data.AuthInterceptor;
import com.taskplanner.data.TaskPlannerApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides @Singleton
    Interceptor provideAuthInterceptor(){
        return new AuthInterceptor();
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient(Interceptor authInterceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(authInterceptor);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        return builder.build();
    }

    @Provides
    @Singleton
    TaskPlannerApi provideTaskPlannerApi(OkHttpClient okHttpClient){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://planner.skillmasters.ga/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(TaskPlannerApi.class);
    }
}
