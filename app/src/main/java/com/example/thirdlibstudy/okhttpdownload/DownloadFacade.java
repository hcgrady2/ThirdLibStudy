package com.example.thirdlibstudy.okhttpdownload;

import android.content.Context;

/**
 * 门面模式
 */
public class DownloadFacade {
    private static final DownloadFacade sFacade = new DownloadFacade();

    private DownloadFacade(){}

    public static DownloadFacade getFacade() {
        return sFacade;
    }

    public void init(Context context){
        //FileManager 初始化,
        FileManager.manager().init(context);
        DaoManagerHelper.getManager().init(context);
    }

    //开始下载
    public void startDownload(String url,DownloadCallback callback){
        DownloadDispatcher.getDispatcher().startDownload(url,callback);
    }

    public void startDownload(String url){
        // DownloadDispatcher.getDispatcher().startDownload(url);
    }
}
