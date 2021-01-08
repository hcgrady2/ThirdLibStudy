package com.example.thirdlibstudy.retrofit.api;

import android.util.Log;

import com.example.thirdlibstudy.retrofit.model.Repo;
import com.example.thirdlibstudy.retrofit.model.RepoSearchResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.thirdlibstudy.retrofit.ConstantConfig.BASE_URL;
import static com.example.thirdlibstudy.retrofit.ConstantConfig.IN_QUALIFIER;
import static com.example.thirdlibstudy.retrofit.ConstantConfig.NETWORK_PAGE_SIZE;

public class GitHubClient {

    private static final String TAG = "GitHubClient";

    private static GitHubClient mInstance;
    private Retrofit retrofit;

    /*
    构造方法，用于单例中生成新的对象
     */
    public GitHubClient() {
        retrofit = new Retrofit.Builder()
                .client(getOkhttpClientBuilder().build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /*
    单例模式，实例化
    关键点：方法加synchronized同步修饰，内部判空
     */
    public static synchronized GitHubClient getInstance() {
        if (mInstance == null) {
            mInstance = new GitHubClient();
        }
        return mInstance;
    }

    /*
    返回实例化后的API
    因为此方法在Client类之内，所以使用方法是：
    先单例模式实例化Client对象，再用Client对象调用Client实例化API
     */
    public GitHubApi getApi() {
        return retrofit.create(GitHubApi.class);
    }

    /*
拦截Http请求并打印日志
 */
    private OkHttpClient.Builder getOkhttpClientBuilder() {
        //日志显示级别。
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        //http拦截器。
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, message);
            }
        });
        loggingInterceptor.setLevel(level);

        //定制OkHttp。
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient
                .Builder();

        okHttpClientBuilder.addInterceptor(loggingInterceptor);
        return okHttpClientBuilder;
    }



}
