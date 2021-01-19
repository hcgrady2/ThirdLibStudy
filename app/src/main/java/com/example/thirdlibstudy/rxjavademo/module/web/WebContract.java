package com.example.thirdlibstudy.rxjavademo.module.web;

import android.app.Activity;

import com.example.thirdlibstudy.rxjavademo.base.BasePresenter;
import com.example.thirdlibstudy.rxjavademo.base.BaseView;


/**
 * WebContract
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  14:38
 */

public interface WebContract {

    interface IWebView extends BaseView {
        Activity getWebViewContext();

        void setGankTitle(String title);

        void loadGankUrl(String url);

        void initWebView();
    }

    interface IWebPresenter extends BasePresenter {
        String getGankUrl();
    }
}
