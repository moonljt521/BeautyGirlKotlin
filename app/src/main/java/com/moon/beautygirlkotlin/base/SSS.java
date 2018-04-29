package com.moon.beautygirlkotlin.base;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * author: moon
 * created on: 18/4/29 下午10:31
 * description:
 */
public class SSS {

    public void a() {

        Observable.just(1)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer.toString()+"111";
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });

    }

}
