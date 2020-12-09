package com.example.glide.requset;

import android.content.Context;
import android.widget.ImageView;

import com.example.glide.listener.RequestListener;
import com.example.glide.utils.MD5Utils;

import java.lang.ref.SoftReference;


/**
 * 封装请求信息(表示一个)，提供 load,loading，into，listener 方法
 */
public class BitmapRequest {

    public BitmapRequest(Context context){
        this.context = context;
    }


    //缓存，不能用 url，有特殊字符，转成 md5
    private String uri;
    private String uriMD5;

    //正在等待的图片
    private int loadingResId;

    //防止内存泄漏
    private SoftReference<ImageView> softReference;

    private Context context;
    private RequestListener listener;


    public BitmapRequest load(String url){
        this.uri = url;
        this.uriMD5 = MD5Utils.toMD5(url);
        return this;
    }

    //随机接口，url 相同，通过另一个参数保证缓存不相同，但是这样，所有的图片的 keyset 都不一样了
    public BitmapRequest load(String url,int i){
        this.uri = url;
        this.uriMD5 = MD5Utils.toMD5(url + String.valueOf(i));
        return this;
    }


    public BitmapRequest loading(int loadingResId){
        this.loadingResId = loadingResId;
        return this;
    }



    public BitmapRequest listener(RequestListener requestListener){
        this.listener = requestListener;
        return this;
    }


    public void into(ImageView imageView){

        this.softReference = new SoftReference<ImageView>(imageView);
        //防止错位
        imageView.setTag(uriMD5);
        RequsetManager.getInstance().addBitmapRequest(this);

    }




    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUriMD5() {
        return uriMD5;
    }

    public void setUriMD5(String uriMD5) {
        this.uriMD5 = uriMD5;
    }

    public int getLoadingResId() {
        return loadingResId;
    }

    public void setLoadingResId(int loadingResId) {
        this.loadingResId = loadingResId;
    }

    public SoftReference<ImageView> getSoftReference() {
        return softReference;
    }

    public void setSoftReference(SoftReference<ImageView> softReference) {
        this.softReference = softReference;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RequestListener getListener() {
        return listener;
    }

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }

    public ImageView getImageView(){
        return softReference.get();
    }
}
