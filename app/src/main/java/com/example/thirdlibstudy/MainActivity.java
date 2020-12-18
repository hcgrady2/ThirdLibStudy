package com.example.thirdlibstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.butterknife_annotations.BindView;
import com.example.butterknife_annotations.TestAnotation;
import com.example.thirdlibstudy.butterknife.ButterknifeMainActivity;
import com.example.thirdlibstudy.eventbus.EventBusOneActivity;
import com.example.thirdlibstudy.glide.GlideMainActivity;
import com.hc.butterknife.ButterKnife;
import com.hc.butterknife.Unbinder;
import com.hc.myokhttp.Call;
import com.hc.myokhttp.Callback;
import com.hc.myokhttp.OkHttpClient;
import com.hc.myokhttp.Request;
import com.hc.myokhttp.RequestBody;
import com.hc.myokhttp.Response;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    Button btnButterKnife;
    Button btnEventBus;
    Button btnGlide;

    @BindView(R.id.btn_okhttp_demo)
    Button btnOkhttp;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        btnButterKnife = findViewById(R.id.btn_butter_knife);
        btnButterKnife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ButterknifeMainActivity.class));
            }
        });


        btnEventBus = findViewById(R.id.btn_event_bus);
        btnEventBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventBusOneActivity.class));
            }
        });


        btnGlide = findViewById(R.id.btn_glide_main);
        btnGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GlideMainActivity.class));

            }
        });



        btnOkhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");


                /**
                 * get 方法没有问题
                 */
//                OkHttpClient client = new OkHttpClient();
//                final Request request = new Request.Builder().url("http://www.baidu.com").build();
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.i(TAG, "onFailure: " + e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                        Log.i(TAG, "onResponse: " + response.string());
//                    }
//                });




                File file = new File("");

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new RequestBody()
                        .type(RequestBody.FORM)
                        .addParam("file", RequestBody.create(file))
                        .addParam("file2", RequestBody.create(file))
                        .addParam("pageSize", 1 + "");

                Request request = new Request.Builder()
                        .url("https://api.saiwuquan.com/api/appv2/sceneModel")
                        .post(requestBody).build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i(TAG, response.string());
                        //Log.e("TAG",response.string());
                    }
                });






            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
