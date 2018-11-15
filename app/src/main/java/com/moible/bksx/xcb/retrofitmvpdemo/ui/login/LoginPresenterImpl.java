package com.moible.bksx.xcb.retrofitmvpdemo.ui.login;

import android.content.Context;
import android.util.Log;

import com.moible.bksx.xcb.retrofitmvpdemo.api.RetrofitApiService;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter.BasePresenter;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;
import com.moible.bksx.xcb.retrofitmvpdemo.http.ProgressSubscriber;
import com.moible.bksx.xcb.retrofitmvpdemo.http.RetrofitManager;
import com.moible.bksx.xcb.retrofitmvpdemo.http.SubscribeOnNextListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static android.support.constraint.Constraints.TAG;


public class LoginPresenterImpl extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{
    public Context mContext;
    public LoginPresenterImpl(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public void getLoginData(String yhm, String mm) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("yhzh",yhm);
        hashMap.put("yhmm",mm);
        JSONObject jsonObject = new JSONObject(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString());
        Observable<ResponseBody> observable =
                RetrofitManager.
                        getInstance().
                        createReq(RetrofitApiService.class).
                        getLoginData(requestBody);
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscribeOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    mView.setLoginData(responseBody);
                    Log.i(TAG, "===onNext: " + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(BaseResultBean t) {
                Log.i(TAG, "===onError: " + t.toString());
            }
        },mContext);
        RetrofitManager.getInstance().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscribe(rxSubscription);
    }

    @Override
    public void getZHPData(String numPage) {
        Observable<ResponseBody> observable =
                RetrofitManager.
                        getInstance().
                        createReq(RetrofitApiService.class).
                        getZPHData(numPage);
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscribeOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    mView.setZHPData(responseBody);
                    Log.i(TAG, "===onNext: " + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(BaseResultBean t) {
                Log.i(TAG, "===onError: " + t.toString());
            }
        },mContext);
        RetrofitManager.getInstance().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscribe(rxSubscription);
    }

}
