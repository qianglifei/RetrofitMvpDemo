package com.moible.bksx.xcb.retrofitmvpdemo.http;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;


public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Context mContext;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    private MaterialDialog pd;
    public ProgressDialogHandler(Context mContext, boolean cancelable, ProgressCancelListener mProgressCancelListener) {
        this.mContext = mContext;
        this.cancelable = cancelable;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    private void showProgressDialog(){
        if (pd == null){
            pd = new MaterialDialog.Builder(mContext)
                    .canceledOnTouchOutside(cancelable)
                    .content("正在加载...")
                    .progress(true, 0)
                    .theme(Theme.LIGHT)
                    .build();
            //对话框弹出后点击或按返回键不消失
            pd.setCancelable(cancelable);
            //dialog弹出后 点击或者按返回键 消失的情况下：
            if (cancelable){
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onProgressCanceled();
                    }
                });
            }

            if (!pd.isShowing()){
                pd.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if (pd != null){
            pd.dismiss();;
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}

