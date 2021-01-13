package com.example.thirdlibstudy.okhttpdownload;


import java.io.File;
import java.io.IOException;

public interface DownloadCallback {
    void onFailure(IOException e);

    void onSucceed(File file);
}
