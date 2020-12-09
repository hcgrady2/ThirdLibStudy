package com.example.glide.listener;

import android.graphics.Bitmap;

public interface RequestListener {

    public boolean onException();

    public boolean onResouceReady(Bitmap resource);
}
