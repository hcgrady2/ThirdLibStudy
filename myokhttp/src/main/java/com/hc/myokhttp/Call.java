package com.hc.myokhttp;

public interface Call {
    void enqueue(Callback callback);

    Response execute();
}
