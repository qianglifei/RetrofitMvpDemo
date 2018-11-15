package com.moible.bksx.xcb.retrofitmvpdemo.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter.BasePresenter;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.view.IBaseView;
import com.moible.bksx.xcb.retrofitmvpdemo.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IBaseView {
    protected T mPresenter;
    protected FragmentActivity mContext;
    private View mRootView;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);//和 BaseActivity 一样，layout() 由子类实现，提供布局。
        mUnbinder = ButterKnife.bind(this, mRootView);
        ButterKnife.bind(this,mRootView);
        mContext = getActivity();
        createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
        initParams();
        initViews();
        return mRootView;
    }

    protected abstract void initViews();

    protected abstract void initParams();

    protected abstract void createPresenter();

    protected abstract int getLayout();

    //统一处理错误信息
    public void handleError(BaseResultBean errResult) {
        if (errResult == null) return;
        if (this == null) return;
        //可以分门别类的处理 错误消息，如session过期，跳转到登录页面。其他情况提示即可
        ToastUtils.showToast(mContext, errResult.getMsg());
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        if (mUnbinder != null) mUnbinder.unbind();
        super.onDestroyView();
    }
}
