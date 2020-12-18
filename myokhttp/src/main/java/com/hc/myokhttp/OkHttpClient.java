package com.hc.myokhttp;

public class OkHttpClient {
    final Dispatcher dispatcher;
    private OkHttpClient(Builder builder) {
        dispatcher = builder.dispatcher;
    }

    public OkHttpClient() {
        this(new Builder());
    }

    public Call newCall(Request request) {
        return RealCall.newCall(request,this);
    }

    public static class Builder{
        Dispatcher dispatcher;
        // 链接超时
        // https 证书的一些参数
        // 拦截器
        // 等等
        public Builder(){
            dispatcher = new Dispatcher();
        }

        public OkHttpClient build(){
            return new OkHttpClient(this);
        }
    }
}
