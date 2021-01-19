package com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.thirdlibstudy.R;
import com.example.thirdlibstudy.rxjavademo.base.ToolbarBaseActivity;
import com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.item.RxConcatMapActivity;
import com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.item.RxCreateActivity;
import com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.item.RxFlatMapActivity;
import com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.item.RxJustActivity;
import com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.item.RxMapActivity;
import com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.item.RxZipActivity;

import butterknife.OnClick;

/**
 * 程序主页面
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-19  13:09
 */

public class RxOperatorsActivity extends ToolbarBaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operators;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected String getSubTitle() {
        return getString(R.string.home);
    }

    @Override
    protected boolean isShowBack() {
        return false;
    }

    @OnClick({R.id.rx_btn_create, R.id.rx_btn_just, R.id.rx_btn_map, R.id.rx_btn_flatMap,R.id.rx_btn_concatMap, R.id.rx_btn_zip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rx_btn_create:
                startActivity(new Intent(this, RxCreateActivity.class));
                break;
            case R.id.rx_btn_just:
                startActivity(new Intent(this, RxJustActivity.class));
                break;
            case R.id.rx_btn_map:
                startActivity(new Intent(this, RxMapActivity.class));
                break;
            case R.id.rx_btn_flatMap:
                startActivity(new Intent(this, RxFlatMapActivity.class));
                break;
            case R.id.rx_btn_concatMap:
                startActivity(new Intent(this, RxConcatMapActivity.class));
                break;
            case R.id.rx_btn_zip:
                startActivity(new Intent(this, RxZipActivity.class));
                break;
        }
    }


}
