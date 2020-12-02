package com.hc.butterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;

public class ButterKnife {
    private ButterKnife() {
        throw new AssertionError("No instances.");
    }

    public final static Unbinder bind(Activity activity) {
        //生成自定义的类
        String viewBindingClassName = activity.getClass().getName() + "_ViewBinding";
        try {
            Class<? extends Unbinder> viewBindingClass = (Class<? extends Unbinder>) Class.forName(viewBindingClassName);
            Constructor<? extends Unbinder> viewBindingConstructor = viewBindingClass.getDeclaredConstructor(activity.getClass());
            Unbinder unbinder = viewBindingConstructor.newInstance(activity);
            return unbinder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Unbinder.EMPTY;
    }
}
