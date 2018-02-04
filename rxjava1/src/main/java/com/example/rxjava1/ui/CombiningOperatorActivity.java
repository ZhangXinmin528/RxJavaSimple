package com.example.rxjava1.ui;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.core.ui.BaseActivity;
import com.example.core.util.LogUtil;
import com.example.rxjava1.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 组合操作符界面
 */

public class CombiningOperatorActivity extends BaseActivity {

    @Override
    protected Object setLayout() {
        return R.layout.activity_operator_combining;
    }

    @Override
    protected void initParamsAndValues() {

    }

    @Override
    protected void initViews() {

        //1.Merge操作符
//        operatorMerge();

        //2.ZIP操作符
        operatorZIP();
    }

    /**
     * 1.Merge操作符
     */
    private void operatorMerge() {
        Observable observableBig =
                Observable
                        .just("A", "B", "C");

        Observable observableSmall =
                Observable
                        .just("a", "b", "c");

        Observable
                .merge(observableSmall, observableBig)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorMerge..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorMerge..onError");
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorMerge..onNext:" + s);
                    }
                });

    }

    /**
     * 2.ZIP操作符
     */
    private void operatorZIP() {

        Observable letterObser =
                Observable.just("你好！", "Hello!", "Hi!");

        Observable numObser =
                Observable.just("A", "B", "C");

        Observable
                .zip(letterObser, numObser, new Func2<String, String, String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s + s2;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorZIP..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorZIP..onError");
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorZIP..onNext:" + s);
                    }
                });
    }

    /**
     * 3.Join操作符
     */
    private void operatorJoin() {

    }


}
