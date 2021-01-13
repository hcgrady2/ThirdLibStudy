package com.example.thirdlibstudy.okhttpdownload;

import android.content.Context;

import com.example.thirdlibstudy.utils.DownUrlUtils;

import java.io.File;

/**
 * FileManager 主要是获取下载的文件,放到了 cache 里面了，后缀没有 .apk
 */
final class FileManager {
    private File mRootDir;
    private Context mContext;
    private static final FileManager sManager = new FileManager();

    public static FileManager manager() {
        return sManager;
    }

    public void init(Context context){
        this.mContext = context.getApplicationContext();
    }

    public void rootDir(File file){
        if(!file.exists()){
            file.mkdirs();
        }

        if(file.exists()&&file.isDirectory()){
            mRootDir = file;
        }
    }

    /**
     * 通过网络的路径获取一个本地文件路径
     * @param url
     * @return
     */
    public File getFile(String url) {
        String fileName = DownUrlUtils.md5Url(url);
        if(mRootDir == null){
            mRootDir = mContext.getCacheDir();
        }
        File file = new File(mRootDir,fileName);
        return file;
    }
}
