package com.example.rxjava1.ui;

import com.example.core.ui.BaseActivity;
import com.example.core.util.LogUtil;
import com.example.rxjava1.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 创建操作符界面
 */

public class CreatingOperatorActivity extends BaseActivity {

    @Override
    protected Object setLayout() {
        return R.layout.activity_operator_creating;
    }

    @Override
    protected void initParamsAndValues() {

    }

    @Override
    protected void initViews() {

        //1.Create 操作符
//        operatorCreate();

        //2.From操作符
//        operatorFrom();

        //3.Just操作符
//        operatorJust();

        //4.Defer操作符
//        operatorDefer();

        //5.Interval操作符
//        operatorInterval();

        //6.Range操作符
//        operatorRange();

        //7.Repeat操作符
//        operatorRepeat();

        //8.Timer操作符
        operatorTimer();
    }


    /**
     * 1.Create 操作符
     */
    private void operatorCreate() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        for (int i = 1; i < 5; i++) {
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                LogUtil.logI("operatorCreate..onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.logI("operatorCreate..onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.logI("operatorCreate..onNext:" + integer);
            }
        });
    }

    /**
     * 2.From操作符
     */
    private void operatorFrom() {
        final String[] arr = new String[]{"元素1", "元素2", "元素3", "元素4", "元素5"};
        Observable.from(arr)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorFrom..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorFrom..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorFrom..onNext:" + s);
                    }
                });
    }

    /**
     * 3.Just操作符
     */
    private void operatorJust() {
        Observable.just("你好！", "Hello!")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorJust..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorJust..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorJust..onNext:" + s);
                    }
                });
    }

    /**
     * 4.Defer操作符
     */
    private void operatorDefer() {
        Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(10);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                LogUtil.logI("operatorDefer..onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.logI("operatorDefer..onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.logI("operatorDefer..onNext:" + integer);
            }
        });
    }

    /**
     * 5.Interval操作符
     */
    private void operatorInterval() {
        Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorInterval..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorInterval..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.logI("operatorInterval..onNext:" + aLong);
                    }
                });
    }

    /**
     * 6.Range操作符
     */
    private void operatorRange() {
        Observable.range(2, 6)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorRange..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorRange..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.logI("operatorRange..onNext:" + integer);
                    }
                });
    }

    /**
     * 7.Repeat操作符
     */
    private void operatorRepeat() {
        Observable.just("你好！")
                .repeat(3)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorRepeat..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorRepeat..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorRepeat..onNext:" + s);
                    }
                });
    }

    /**
     * 8.Timer操作符
     */
    private void operatorTimer() {
        LogUtil.logIWithTime("开始");
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorTimer..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorTimer..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.logIWithTime("onNext");
                        LogUtil.logI("operatorTimer..onNext:" + aLong);
                    }
                });
    }

}
