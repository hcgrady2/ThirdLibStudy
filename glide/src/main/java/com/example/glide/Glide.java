package com.example.glide;

import android.app.Activity;
import android.app.FragmentManager;

import com.example.glide.lifecycle.RequestManagerFragment;
import com.example.glide.requset.BitmapRequest;


/**
 *  对外提供 with 方法，返回一个 BitmapRequest 对象。
 */
public class Glide {
    public static BitmapRequest with(Activity activity){

        //不能每加载一个图片就绑定一个
        //todo，不同的 activity 测试
        FragmentManager manager =  activity.getFragmentManager();
        RequestManagerFragment current = (RequestManagerFragment)manager.findFragmentByTag("com.study.imagloaderdemo");

        if (current == null){
            current = new RequestManagerFragment();
            manager.beginTransaction().add(current,"com.study.imagloaderdemo").commitAllowingStateLoss();
        }
        return new BitmapRequest(activity);
    }
}

