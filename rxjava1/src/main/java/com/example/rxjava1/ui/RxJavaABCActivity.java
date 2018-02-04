package com.example.rxjava1.ui;

import android.content.Context;

import com.example.core.ui.BaseActivity;
import com.example.core.util.LogUtil;
import com.example.rxjava1.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;


/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * RxJava Version 1.x版本
 * 初识
 */

public class RxJavaABCActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected Object setLayout() {
        return R.layout.activity_abc;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
    }

    @Override
    protected void initViews() {

        //1.打印HelloWorld
        sayHello();

        //2. 简化写法
        simplySayHello();
    }

    /**
     * 初识RxJava 1.x
     */
    private void sayHello() {
        //被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hellow World!");
                subscriber.onCompleted();
            }
        });
        //观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LogUtil.logI("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.logI("onError");
            }

            @Override
            public void onNext(String s) {
                LogUtil.logI("onNext..结果：" + s);
            }
        };

        observable.subscribe(subscriber);

    }

    /**
     * 2.Say Hello 简化写法
     */
    private void simplySayHello() {
        Observable.just("Hello World!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.logI("call..结果：" + s);
                    }
                });
    }

}
