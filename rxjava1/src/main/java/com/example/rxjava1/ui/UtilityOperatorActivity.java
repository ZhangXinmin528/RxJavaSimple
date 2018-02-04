package com.example.rxjava1.ui;

import com.example.core.ui.BaseActivity;
import com.example.core.util.LogUtil;
import com.example.rxjava1.R;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 辅助性操作符界面
 */

public class UtilityOperatorActivity extends BaseActivity {

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

        //1.Delay操作符
//        operatorDelay();

        //2.Do操作符
//        operatorDo();

        //3.Materialize操作符
//        operatorMaterialize();

        //4.SubscribeOn操作符
//        operatorSubscribeOn();

        //5.TimeInterval操作符
//        operatorTimeInterval();

        //6.Timeout操作符
//        operatorTimeout();

        //7.Timestamp操作符
//        operatorTimestamp();

        //8.Using操作符
//        operatorUsing();

        //9.To操作符
        operatorTo();
    }

    /**
     * 1.Delay操作符
     */
    private void operatorDelay() {
        Observable<Long> observable =
                Observable.interval(1, TimeUnit.SECONDS)
                        .take(5);
        LogUtil.logIWithTime("operatorDelay..create observable");
        //delay
        observable
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logIWithTime("operatorDelay..delay..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorDelay..delay..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.logIWithTime("operatorDelay..delay..onNext:" + aLong);
                    }
                });

        //delaySubscription
        observable.delaySubscription(10, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logIWithTime("operatorDelay..delaySubscription..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorDelay..delaySubscription..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.logIWithTime("operatorDelay..delaySubscription..onNext:" + aLong);
                    }
                });
    }

    /**
     * 2.Do操作符
     */
    private void operatorDo() {
        Observable
                .just(1, 2, 3, 4, 5, 6)
                //Observable每发射一项数据就会调用一次
                .doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        LogUtil.logIWithTime("operatorDo..doOnEach:" + notification.getValue());
                    }
                })
                //执行OnError时会调用该方法
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.logIWithTime("operatorDo..doOnError:" + throwable.getMessage());
                    }
                })
                //终止时会调用
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        LogUtil.logIWithTime("operatorDo..doOnTerminate");
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logIWithTime("operatorDo..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logIWithTime("operatorDo..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.logIWithTime("operatorDo..onNext:" + integer);
                    }
                });
    }

    /**
     * 3.Materialize操作符
     */
    private void operatorMaterialize() {

        Observable
                .just("Hi!", "Hello", "你好")
                .materialize()
                .subscribe(new Action1<Notification<String>>() {
                    @Override
                    public void call(Notification<String> stringNotification) {
                        LogUtil.logI("operatorMaterialize..materialize..call:[Kind:"
                                + stringNotification.getKind() + "..Value:"
                                + stringNotification.getValue() + "]");
                    }
                });

    }

    /**
     * 4.SubscribeOn操作符
     */
    private void operatorSubscribeOn() {
        Observable
                .just("Android", "IOS")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        LogUtil.logI("operatorSubscribeOn..map..Thread:" + Thread.currentThread().getId());
                        return s + "..Developer";
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.logI("operatorSubscribeOn..Thread:" + Thread.currentThread().getId());
                        LogUtil.logI("operatorSubscribeOn..call:" + s);
                    }
                });
    }

    /**
     * 5.TimeInterval操作符
     */
    private void operatorTimeInterval() {
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 3; i <= 7; i++) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .timeInterval()
                .subscribe(new Subscriber<TimeInterval<Integer>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorTimeInterval..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorTimeInterval..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(TimeInterval<Integer> integerTimeInterval) {
                        LogUtil.logI("operatorTimeInterval..onNext:[Value:" + integerTimeInterval.getValue()
                                + "..IntervalInMilliseconds:"
                                + integerTimeInterval.getIntervalInMilliseconds() + "]");
                    }
                });
    }

    /**
     * 6.Timeout操作符
     */
    private void operatorTimeout() {
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(i * 100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .timeout(200, TimeUnit.MILLISECONDS, Observable.just(100, 200))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorTimeout..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorTimeout..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.logI("operatorTimeout..onNext:" + integer);
                    }
                });
    }

    /**
     * 7.Timestamp操作符
     */
    private void operatorTimestamp() {
        Observable
                .just(1, 2, 3, 4, 5)
                .timestamp()
                .subscribe(new Subscriber<Timestamped<Integer>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logI("operatorTimestamp..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logI("operatorTimestamp..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Timestamped<Integer> integerTimestamped) {
                        LogUtil.logI("operatorTimestamp..onNext:[Value:" + integerTimestamped.getValue() +
                                "..TimestampMillis:" + integerTimestamped.getTimestampMillis() + "]");
                    }
                });
    }

    /**
     * 8.Using操作符
     */
    private void operatorUsing() {
        Observable
                .using(new Func0<Integer>() {
                           @Override
                           public Integer call() {
                               return new Random().nextInt(100);
                           }
                       }, new Func1<Integer, Observable<String>>() {
                           @Override
                           public Observable<String> call(Integer integer) {
                               LogUtil.logI("operatorUsing..创建资源");
                               return Observable.just("数字内容：" + integer);
                           }
                       }, new Action1<Integer>() {
                           @Override
                           public void call(Integer integer) {
                               LogUtil.logI("operatorUsing..释放资源");
                           }
                       }
                )
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.logI("operatorUsing..call:" + s);
                    }
                });
    }

    /**
     * 9.To操作符
     */
    private void operatorTo() {
        Observable
                .just("Hello", "Hi", "你好")
                .toList()
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        LogUtil.logI("operatorTo..call:" + strings);
                    }
                });
    }

}
