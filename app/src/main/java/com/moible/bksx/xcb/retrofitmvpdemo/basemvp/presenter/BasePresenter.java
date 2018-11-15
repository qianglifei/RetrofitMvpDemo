package com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter;

import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.view.IBaseView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter基类，目的是统一处理绑定和解绑
 * @param <T>
 */
public class BasePresenter<T extends IBaseView> implements IPresenter<T> {
    protected T mView;
    /**
     * 对象订阅者集合
     * 这个类的内部是由Set<Subscription> 维护订阅者
     */
    private CompositeSubscription mCompositeSubscription;
    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }
    //解绑订阅者
    private void unSubscribe() {
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }
    //增加订阅者
    //加入集合,才能在及时取消订阅
    protected void addSubscribe(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }else {
            mCompositeSubscription.add(subscription);
        }
    }
}
