package com.example.glide.lifecycle;

import android.app.Activity;
import android.app.Fragment;

/**
 * 生命周期管理，观察者
 */
public class RequestManagerFragment extends Fragment {

    Activity mActivity;

    private int activityCode;

    LifecycleObservable lifecycleObservable = LifecycleObservable.getInstance();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //mActivity = activity;
        activityCode  = activity.hashCode();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //这里就是 glide 图片生命周期管理
        lifecycleObservable.onDestroy(activityCode);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
