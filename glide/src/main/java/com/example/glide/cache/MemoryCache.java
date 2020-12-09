package com.example.glide.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.example.glide.requset.BitmapRequest;
import com.example.glide.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mLruCache;

    //图片的 md5 -> activityCode，保存的是图片和 activity 的映射关系
    private HashMap<String,Integer> activityCache;


    private static volatile MemoryCache instance;

    private static final byte[] lock = new byte[0];

    public static MemoryCache getInstance(){
        if (instance == null){
            synchronized (lock){
                if (instance == null){
                    instance = new MemoryCache();
                }
            }
        }
        return instance;
    }



    public MemoryCache() {
        Log.i(Constants.TAG, "MemoryCache，已经初始化 ");
     /*   int maxSize = (int) (Runtime.getRuntime().freeMemory() / 1024 / 8);
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //一张图片的大小
                //return value.getRowBytes() * value.getHeight();
                return value.getRowBytes() * value.getHeight()/1024;
            }
        };
*/
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };



        activityCache = new HashMap<>();

    }







    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (bitmap != null){
            if (mLruCache.get(request.getUriMD5()) == null){
                Log.i(Constants.TAG, "start to put:" + request.getUriMD5());
                mLruCache.put(request.getUriMD5(), bitmap);


                Bitmap bitmap3  = mLruCache.get(request.getUriMD5());
                if (bitmap3 != null && !bitmap.isRecycled()){
                    //真正的图片回收
                    Log.i(Constants.TAG, "bitmap is not null " );
                }else {
                    Log.i(Constants.TAG, "bitmap is  null !!!!!!!!!" );
                }





            }
            activityCache.put(request.getUriMD5(),request.getContext().hashCode());


        }
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return mLruCache.get(request.getUriMD5());
    }

    @Override
    public void remove(BitmapRequest request) {
        mLruCache.remove(request.getUriMD5());
    }

    public void remove(int activityCode) {
        Log.i(Constants.TAG, "remove: ");
        //  mLruCache.remove(request.getUriMD5());
        //移除当前 activity 所有  bitmap
        List<String> tempUriMdeList = new ArrayList<>();
        //变了所有缓存的图片，activity 所关联的图片缓存
        for (String uriMd5 : activityCache.keySet()){
            if (activityCache.get(uriMd5).intValue() == activityCode){
                // activityCache.remove(uriMd5); 不能这么做，这只是把映射关系移除了
                tempUriMdeList.add(uriMd5);
            }
        }

        //移除
        for (String uriMd5: tempUriMdeList){
            Log.i(Constants.TAG, "remove md5 " + uriMd5);
            activityCache.remove(uriMd5);
            Bitmap bitmap  = mLruCache.get(uriMd5);
            if (bitmap != null && !bitmap.isRecycled()){
                //真正的图片回收
                bitmap.recycle();
                Log.i(Constants.TAG, "bitmap is not null " );
            }else {
                Log.i(Constants.TAG, "bitmap is  null !!!!!!!!!" );
            }
            mLruCache.remove(uriMd5);
            bitmap = null;
        }
        if (!tempUriMdeList.isEmpty()){
            Log.i(Constants.TAG, "suggest to gc ");
            System.gc();
            try {
                finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}