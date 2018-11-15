package com.moible.bksx.xcb.retrofitmvpdemo.test;

import android.content.Context;
import android.util.Log;

import com.moible.bksx.xcb.retrofitmvpdemo.api.RetrofitApiService;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.ReturnDataBean;
import com.moible.bksx.xcb.retrofitmvpdemo.http.HttpResultFunc;
import com.moible.bksx.xcb.retrofitmvpdemo.http.ProgressSubscriber;
import com.moible.bksx.xcb.retrofitmvpdemo.http.RetrofitManager;
import com.moible.bksx.xcb.retrofitmvpdemo.http.SubscribeOnNextListener;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter.BasePresenter;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class TestPresenterImpl extends BasePresenter<TestContract.View> implements TestContract.Presenter {
    public Context mContext;
    public TestPresenterImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getMovieListData(String yhm, String mm,String version,String imsi) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username",yhm);
        hashMap.put("password",mm);
        hashMap.put("version",version);
        hashMap.put("imsi",imsi);
        Observable<ReturnDataBean> observable =
                RetrofitManager.
                        getInstance().
                        createReq(RetrofitApiService.class).
                        getMovieListData(hashMap).
                        map(new HttpResultFunc<>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscribeOnNextListener<ReturnDataBean>() {
            @Override
            public void onNext(ReturnDataBean returnDataBean) {
                mView.setMovieListData(returnDataBean);
            }

            @Override
            public void onError(BaseResultBean t) {
                Log.i("TAG", "===onError: " + t.getMsg());
            }
        },mContext);
        RetrofitManager.getInstance().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscribe(rxSubscription);
    }

}
