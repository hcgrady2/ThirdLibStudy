package com.example.thirdlibstudy.eventbus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdlibstudy.R;
import com.hc.myeventbus.EventBus;
import com.hc.myeventbus.Subscribe;
import com.hc.myeventbus.ThreadMode;

public class EventBusTwoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_two;
    private Button btn_;
    private Button btn_back;
    private Button btn_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_two);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        tv_two = (TextView) findViewById(R.id.tv_two);
        btn_ = (Button) findViewById(R.id.btn_);

        btn_.setOnClickListener(this);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_in = (Button) findViewById(R.id.btn_in);
        btn_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_:
                EventBus.getDefault().post(new EventNotify("哥哥，我才是你哥哥"));

                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_in:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventNotify("哥哥，我才是你哥哥"));
                    }
                }).start();
                break;
        }
    }

    @Subscribe(sticky = true)
    public void setText(String str) {
        Log.e("TwoActivity", "methodName :" + getCurrentMethodName() + "   thread : " + Thread.currentThread().getId() + "  msg :" + str);
        tv_two.setText("MainActivity:" + str);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void setTextASY(String str) {
        Log.e("TwoActivity", "methodName :" + getCurrentMethodName() + "   thread : " + Thread.currentThread().getId() + "  msg :" + str);
        tv_two.setText("MainActivity:" + str);
    }

//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN, priority = 1)
//    public void setTextInMain(String str) {
//        Log.e("TwoActivity", "methodName :" + getCurrentMethodName() + "   thread : " + Thread.currentThread().getId() + "  msg :" + str);
//        tv_two.setText("MainActivity:" + str);
//    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unRegister(this);
        super.onDestroy();
    }

    public static String getCurrentMethodName() {
        int level = 1;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String methodName = stacks[level].getMethodName();
        return methodName;
    }
}
