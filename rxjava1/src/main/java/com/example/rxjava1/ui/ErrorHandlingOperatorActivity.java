package com.example.rxjava1.ui;

import com.example.core.ui.BaseActivity;
import com.example.core.util.LogUtil;
import com.example.rxjava1.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 错误操作符界面
 */

public class ErrorHandlingOperatorActivity extends BaseActivity {

    private int count;

    @Override
    protected Object setLayout() {
        return R.layout.activity_operator_combining;
    }

    @Override
    protected void initParamsAndValues() {

    }

    @Override
    protected void initViews() {

        //1.OnErrorReturn操作符
//        operatorOnErrorReturn();

        //onErrorResumeNext操作符
//        operatoronErrorResumeNext();

        //3.onExceptionResumeNext操作符
//        operatoronExceptionResumeNext();

        //4.4.Retry操作符
//        operatorRetry();

        //5.RetryWhen操作符
        operatorRetryWhen();
    }

    /**
     * 1.OnErrorReturn操作符
     */
    private void operatorOnErrorReturn() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        for (int i = 0; i < 6; i++) {
                            if (i % 2 == 0) {
                                subscriber.onNext(i + "");
                            } else {
                                subscriber.onError(new Throwable("奇数"));
                            }
                        }
                    }
                })
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        return "当前序号是奇数！";
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorOnErrorReturn..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorOnErrorReturn..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorOnErrorReturn..onNext:" + s);
                    }
                });
    }

    /**
     * 2.onErrorResumeNext操作符
     */
    private void operatoronErrorResumeNext() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        for (int i = 0; i < 6; i++) {
                            if (i % 2 == 0) {
                                subscriber.onNext(i + "");
                            } else {
                                subscriber.onError(new Throwable("奇数"));
                            }
                        }
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(Throwable throwable) {
                        return Observable.just("A", "B", "C");
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatoronErrorResumeNext..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatoronErrorResumeNext..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatoronErrorResumeNext..onNext:" + s);
                    }
                });
    }

    /**
     * 3.onExceptionResumeNext操作符
     * 这个方法有啥用？I have no idea!
     */
    private void operatoronExceptionResumeNext() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        for (int i = 0; i < 6; i++) {
                            if (i % 2 == 0) {
                                subscriber.onNext(i + "");
                            } else {
                                subscriber.onError(new Throwable("当前为奇数"));
                            }
                        }
                    }
                })
                .onExceptionResumeNext(Observable.just("这是一个新的Observable!"))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatoronExceptionResumeNext..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatoronExceptionResumeNext..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatoronExceptionResumeNext..onNext:" + s);
                    }
                });
    }

    /**
     * 4.Retry操作符
     */
    private void operatorRetry() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        for (int i = 0; i < 6; i++) {
                            if (i % 2 == 0) {
                                subscriber.onNext(i + "");
                            } else {
                                subscriber.onError(new Throwable("当前为奇数"));
                            }
                        }
                    }
                })
                .retry(3)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorRetry..retry(3)..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorRetry..retry(3)..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorRetry..retry(3)..onNext:" + s);
                    }
                });

        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        for (int i = 0; i < 6; i++) {
                            if (i % 2 == 0) {
                                subscriber.onNext(i + "");
                            } else {
                                subscriber.onError(new Throwable("当前为奇数"));
                            }
                        }
                    }
                })
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        LogUtil.logE("第" + integer + "次错误：" + throwable.getMessage());
                        if (integer > 2)
                            return false;
                        else
                            return true;
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorRetry..retry(Func2)..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorRetry..retry(Func2)..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.logI("operatorRetry..retry(Func2)..onNext:" + s);
                    }
                });
    }

    /**
     * 5.RetryWhen操作符
     */
    private void operatorRetryWhen() {
        final int maxCount = 3;

        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        LogUtil.logI("operatorRetryWhen..create");
                        subscriber.onError(new RuntimeException("create failed"));
                    }
                })
                .subscribeOn(Schedulers.immediate())
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (count++ < maxCount) {
                                    LogUtil.logI("尝试第" + count + "次！");
                                    return Observable.timer(1000, TimeUnit.MILLISECONDS);
                                } else {
                                    return Observable.error(throwable);
                                }
                            }
                        });
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorRetryWhen..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorRetryWhen..onError:" + e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.logI("operatorRetryWhen..onNext:" + integer);
                    }
                });
    }
}
