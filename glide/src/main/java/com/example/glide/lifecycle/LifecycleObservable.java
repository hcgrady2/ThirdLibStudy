package com.example.glide.lifecycle;

import com.example.glide.cache.DoubleCache;

public class LifecycleObservable {

    private static LifecycleObservable instance;

    public static LifecycleObservable getInstance(){
        if (instance == null){
            synchronized (LifecycleObservable.class){
                if (instance == null){
                    instance = new LifecycleObservable();
                }
            }
        }
        return instance;
    }


    public void onStart(int activityCode){

    }

    public void onStop(int activityCode){

    }

    public void onDestroy(int activityCode){
        DoubleCache.getInstance().remove(activityCode);
    }


}
