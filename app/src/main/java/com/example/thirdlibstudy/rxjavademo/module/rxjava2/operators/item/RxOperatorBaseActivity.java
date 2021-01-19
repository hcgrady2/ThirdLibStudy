package com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.item;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.thirdlibstudy.R;
import com.example.thirdlibstudy.rxjavademo.base.ToolbarBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 每一种RxJava 2.x 操作符的基类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-19  17:00
 */

public abstract class RxOperatorBaseActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    protected Button mRxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    protected TextView mRxOperatorsText;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected abstract String getSubTitle();

    protected abstract void doSomething();


    @OnClick(R.id.rx_operators_btn)
    public void onViewClicked() {
        mRxOperatorsText.append("\n");
        doSomething();
    }
}
