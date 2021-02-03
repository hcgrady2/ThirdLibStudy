package com.example.thirdlibstudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.butterknife_annotations.BindView;
import com.example.thirdlibstudy.butterknife.ButterknifeMainActivity;
import com.example.thirdlibstudy.eventbus.EventBusOneActivity;
import com.example.thirdlibstudy.glide.GlideLoadImgMain;
import com.example.thirdlibstudy.glide.GlideMainActivity;
import com.example.thirdlibstudy.okhttpdownload.DownloadCallback;
import com.example.thirdlibstudy.okhttpdownload.DownloadFacade;
import com.example.thirdlibstudy.retrofit.RetrofitMainActivity;
import com.example.thirdlibstudy.robust.RobustMain;
import com.example.thirdlibstudy.rxjavademo.module.HomeActivity;
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

import static com.example.thirdlibstudy.Constants.apkUrl;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    Button btnButterKnife;
    Button btnEventBus;
    Button btnGlide;

    @BindView(R.id.btn_okhttp_demo)
    Button btnOkhttp;

    @BindView(R.id.btn_retrofit_demo)
    Button btnRetrofit;


    @BindView(R.id.btn_okhttp_download)
    Button btnMultiThreadDown;


    @BindView(R.id.btn_glide_load)
    Button btnLoadImg;

    @BindView(R.id.btn_rxjava2_demo)
    Button btnRxjava;




    @BindView(R.id.btn_robust_demo)
    Button btnRobust;


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

        btnLoadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GlideLoadImgMain", "onClick: ");
                startActivity(new Intent(MainActivity.this, GlideLoadImgMain.class));
            }
        });
        btnRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetrofitMainActivity.class));

            }
        });


        btnRxjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
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


        btnMultiThreadDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"start download....",Toast.LENGTH_SHORT).show();


                // 有三个类需要用户去关注，后面我们有可能会自己去更新代码，用户就需要换调用方式
                // 调用的方式 门面
                DownloadFacade.getFacade().init(MainActivity.this);

                DownloadFacade.getFacade()
                        .startDownload(apkUrl, new DownloadCallback() {
                            @Override
                            public void onFailure(IOException e) {
                                e.printStackTrace();
                                Log.i(TAG, "onFailure: " + e.toString());
                            }

                            @Override
                            public void onSucceed(File file) {
                                Log.i(TAG, "onSucceed: ");
                                btnMultiThreadDown.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this,"download success !",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //下载完成之后进行安装
                                installFile(file);
                            }
                        });




            }
        });





        btnRobust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RobustMain.class));

            }
        });



    }

    private void installFile(File  file) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file );
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
