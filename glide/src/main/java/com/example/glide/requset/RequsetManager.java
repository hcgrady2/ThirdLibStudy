package com.example.glide.requset;

import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;

//有一个队列
public class RequsetManager {

    private static final String TAG = "whctag";
    private LinkedBlockingDeque<BitmapRequest> requestQueue = new LinkedBlockingDeque<>();

    public RequsetManager() {
        start();
    }

    //转发器管理
    private BitmapDispatcher[] dispatchers;


    private void start() {
        Log.i(TAG, "start: 准备开始");
        stop();
        int threadCount = Runtime.getRuntime().availableProcessors();
        dispatchers = new BitmapDispatcher[threadCount];

        for (int i = 0; i < dispatchers.length; i++){
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();
            dispatchers[i]  = bitmapDispatcher;
        }
    }

    /**
     * 当程序第一次运行，并初始化  glide 的时候，应该没有请求也没有 dispatcher，因此如有运行的先 interrupted 掉
     */
    private void stop(){
        if (dispatchers != null && dispatchers.length > 0){
            for (BitmapDispatcher bitmapDispatcher : dispatchers){
                if (!bitmapDispatcher.isInterrupted()){
                    bitmapDispatcher.interrupt();
                }
            }
        }
    }

    private static RequsetManager instance;
    public static RequsetManager getInstance(){
        if (instance == null){
            synchronized (RequsetManager.class){
                if (instance == null){
                    instance = new RequsetManager();
                }
            }
        }
        return  instance;
    }


    //请求入队
    public void addBitmapRequest(BitmapRequest request){

        if (!requestQueue.contains(request)){
            requestQueue.add(request);
        }else {
            Log.i("GlideDemo", "addBitmapRequest: 任务已经添加，不用重复添加");
        }
    }


}
