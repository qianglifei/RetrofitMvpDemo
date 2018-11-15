package com.moible.bksx.xcb.retrofitmvpdemo.http;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;



import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{
    private SubscribeOnNextListener mSubscribeOnNextListener;
    private Context mContext;
    private ProgressDialogHandler mProgressDialogHandler;

    public ProgressSubscriber(SubscribeOnNextListener subscribeOnNextListener,Context context){
        mContext = context;
        mSubscribeOnNextListener = subscribeOnNextListener;
        mProgressDialogHandler = new ProgressDialogHandler(context,true,this);
    }

    /**
     * 在开始订阅的时候显示加载框
     */
    @Override
    public void onStart() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 加载完成时，进行隐藏
     */
    @Override
    public void onCompleted() {
        Toast.makeText(mContext, "数据加载完成", Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
    }

    /**
     * 出错时，也进行隐藏
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException){
            Toast.makeText(mContext, "网络连接超时，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else if (e instanceof ConnectException){
            Toast.makeText(mContext, "网络连接异常，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("TAG", "====onError: " + e.toString());
        }
        dismissProgressDialog();
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null){
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onNext(T t) {
        mSubscribeOnNextListener.onNext(t);
    }

    @Override
    public void onProgressCanceled() {
        if (!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
