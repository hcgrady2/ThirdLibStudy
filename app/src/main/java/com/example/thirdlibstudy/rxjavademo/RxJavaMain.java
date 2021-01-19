package com.example.thirdlibstudy.rxjavademo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.butterknife_annotations.BindView;
import com.example.thirdlibstudy.R;
import com.hc.butterknife.ButterKnife;
import com.hc.butterknife.Unbinder;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxJavaMain extends AppCompatActivity {

    private static final String TAG = "RxJavaMainTag";

    @BindView(R.id.btn_use)
    Button btnUse;

    Unbinder unbinder;


    FutureTask < String > futureTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_main);
        unbinder = ButterKnife.bind(this);

        initUse();
    }

    private void initUse() {
        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //useJxJava();
                //UseJust();
                UserFuture();
            }
        });

    }

    private void UserFuture() {
      futureTask = new FutureTask< >(new Callable< String >() {
            @Override
            public String call() throws Exception {
                Log.d(TAG, "CallableDemo is Running");
                return "返回结果";
            }
        });

        Observable.fromFuture(futureTask)
                .doOnSubscribe(new Consumer< Disposable >() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        futureTask.run();
                    }
                })
                .subscribe(new Consumer < String > () {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "================accept " + s);
                    }
                });



    }

    private void UseJust() {
        Observable.just(1, 2, 3)
                .subscribe(new Observer < Integer > () {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "=================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "=================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=================onComplete ");
                    }
                });

    }

    private void useJxJava(){
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });


        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "======================onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "======================onNext " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "======================onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "======================onComplete");
            }
        };


        observable.subscribe(observer);

    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
