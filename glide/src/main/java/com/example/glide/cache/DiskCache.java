package com.example.glide.cache;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.glide.cache.disk.DiskLruCache;
import com.example.glide.cache.disk.IOUtil;
import com.example.glide.requset.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskCache implements BitmapCache {
    private static DiskCache mDiskCache;
    //缓存路径 ,这里可以换成你想要保存的名字和路径
    private String mCacheDir = "testImage";
    //MB
    private static final int MB = 1024 * 1024;

    private DiskLruCache mDiskLruCache;

    public DiskCache(Context context) {
        initDiskCache(context);
    }

    public static DiskCache getInstance(Context context) {
        if (mDiskCache == null) {
            synchronized (DiskCache.class) {
                if (mDiskCache == null) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }

    private void initDiskCache(Context context) {
        //得到缓存的目录 data/data目录下，好处，app卸载后，文件自动被删除
        File directory = getDiskCacheDir(mCacheDir, context);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            //mxSize 指定缓存容量
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDiskCacheDir(String mCacheDir, Context context) {
        File cacheDir = context.getCacheDir();
        File newCacheDir = new File(cacheDir, mCacheDir);

        return newCacheDir;
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream os = null;
        try {
            //路径必须是合法字符
            editor = mDiskLruCache.edit(request.getUriMD5());
            //同时加载同一张图片，这里的 editor 可能为 null,获取不到
            if (editor == null){
                return;
            }
            os = editor.newOutputStream(0);
            if (os != null) {
                if (persistBitmap2Disk(bitmap, os)) {
                    editor.commit();
                } else {
                    editor.abort();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
        }
    }

    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {



        //图片质量压缩
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtil.closeQuietly(bos);
        }

        return true;
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(request.getUriMD5());
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}