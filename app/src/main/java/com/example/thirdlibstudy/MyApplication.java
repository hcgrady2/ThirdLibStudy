package com.example.thirdlibstudy;

import android.app.Application;

import com.example.glide.cache.DoubleCache;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DoubleCache cache = DoubleCache.getInstance(this);

    }
}
