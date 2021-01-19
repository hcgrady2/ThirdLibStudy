package com.example.thirdlibstudy.rxjavademo.module.web;

import android.app.Activity;
import android.content.Intent;


/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  14:41
 */

public class WebPresenter implements WebContract.IWebPresenter {
    private WebContract.IWebView mWebView;
    private String mGankUrl;
    private Activity mActivity;

    public WebPresenter(WebContract.IWebView webView){
        this.mWebView = webView;
    }


    @Override
    public void subscribe() {
        mActivity = mWebView.getWebViewContext();
        Intent intent = mActivity.getIntent();
        mWebView.setGankTitle(intent.getStringExtra(WebViewActivity.GANK_TITLE));
        mWebView.initWebView();
        mGankUrl = intent.getStringExtra(WebViewActivity.GANK_URL);
        mWebView.loadGankUrl(mGankUrl);
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public String getGankUrl() {
        return this.mGankUrl;
    }
}
