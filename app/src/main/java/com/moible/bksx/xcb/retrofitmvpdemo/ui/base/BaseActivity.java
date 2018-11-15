package com.moible.bksx.xcb.retrofitmvpdemo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.moible.bksx.xcb.retrofitmvpdemo.application.BaseApplication;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter.BasePresenter;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.view.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;



public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView,Toolbar.OnMenuItemClickListener {
    protected T mPresenter;
    protected Context mContext = this;
    private Unbinder mUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
        BaseApplication.getInstance().addActivity(this);

        //初始化View
        initView();

        //初始化事件
        initEvent();

    }

    public abstract void initView();
    public abstract void initEvent();
    public abstract int getLayoutId();
    protected abstract void createPresenter(); //创建presenter
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * @param toolbar toolbar 控件
     * @param title   标题
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        if (toolbar != null) {
            if (title != null) toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            toolbar.setOnMenuItemClickListener(this);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }
    //toolbar右侧menu点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
    //统一处理错误信息
    public void handleError(BaseResultBean errResult) {
        if (errResult == null) return;
        if (this == null) return;
        //可以分门别类的处理 错误消息，如session过期，跳转到登录页面。其他情况提示即可
        Toast.makeText(mContext, "" + errResult.getMsg(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        if (mUnbinder != null) mUnbinder.unbind();
        super.onDestroy();
    }
}
