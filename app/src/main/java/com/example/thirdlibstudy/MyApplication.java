package com.example.thirdlibstudy;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.example.glide.cache.DoubleCache;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DoubleCache cache = DoubleCache.getInstance(this);

        AndroidNetworking.initialize(getApplicationContext());


    }
}
