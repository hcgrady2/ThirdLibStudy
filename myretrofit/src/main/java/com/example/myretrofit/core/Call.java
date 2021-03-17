package com.example.myretrofit.core;

/**
 * Created by hcDarren on 2017/12/17.
 */

public interface Call<T> {
    void enqueue(Callback<T> callback);
}
