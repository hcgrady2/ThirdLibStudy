package com.example.glide.cache;

import android.graphics.Bitmap;

import com.example.glide.requset.BitmapRequest;

public interface BitmapCache {
    /**
     * 缓存bitmap
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 通过请求去bitmap
     * @param request
     * @return
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除bitmap
     * @param request
     */
    void remove(BitmapRequest request);
}
