package com.example.movieapp;

import com.example.movieapp.request.MovieApiClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    //    Singleton Pattren
    private static AppExecutors instance;

    //  Java  Singleton Pattren Implemented
    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

//   We need to do Background Retrofits Calls without showing it in UI
//    And ScheduleExecutorsService Get the Data from Retrofit web apis and send it to Live data and then repository and
//    then VIewmodel and then Main UI
// THis function makes background Threads..

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

//    1st for Connecting with retrofit, 2nd for cancelling retrofit and later on we will implement 3rd one......
    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }

}
