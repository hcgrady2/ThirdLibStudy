package com.example.thirdlibstudy.glide;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.thirdlibstudy.R;


public class GlideLoadImgMain extends AppCompatActivity {

    private static final String TAG = "GlideLoadImgMain";
    String URL = "http://www-ecs.didapinche.com/pics//g/egcwp75fo6p_vv6zsy1nwic.jpg";
    String URL2 = "http://pic22.nipic.com/20120714/9622064_105642209176_2.jpg";

    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_load);

        statusBarHide(this);
       // NavigationBarStatusBar(this,true);
        Log.i(TAG, "onCreate: ");
        imageView = findViewById(R.id.image_ad);

        Glide.with(GlideLoadImgMain.this)
                .load(URL)
                .skipMemoryCache(true)
                .into(imageView);


        Log.i(TAG, "onCreate: 加载图片");
    }


    /**
     * 设置Activity的statusBar隐藏
     * @param activity
     */
    public  void statusBarHide(Activity activity){
        // 代表 5.0 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            //ActionBar actionBar = activity.getActionBar();
            //actionBar.hide();
            return;
        }

        // versionCode > 4.4  and versionCode < 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    /**
     * 导航栏，状态栏隐藏
     * @param activity
     */
    public  void NavigationBarStatusBar(Activity activity,boolean hasFocus){
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }




}
