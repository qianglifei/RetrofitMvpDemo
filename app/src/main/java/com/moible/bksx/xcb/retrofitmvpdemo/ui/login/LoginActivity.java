package com.moible.bksx.xcb.retrofitmvpdemo.ui.login;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.moible.bksx.xcb.retrofitmvpdemo.R;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;
import com.moible.bksx.xcb.retrofitmvpdemo.ui.base.BaseActivity;
import com.moible.bksx.xcb.retrofitmvpdemo.util.LogUtils;

import butterknife.BindView;
import okhttp3.ResponseBody;

public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginContract.View {

    @BindView(R.id.button)
    Button button;

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new LoginPresenterImpl(mContext);
    }

    @Override
    public void showError(BaseResultBean resultBean) {

    }

    @Override
    public void setLoginData(ResponseBody bean) {
        Log.i("TAG", "===setLoginData: " + bean);
        getZhu();
    }

    @Override
    public void setZHPData(ResponseBody we) {
        LogUtils.i("TAG","===setLogin" + we);
    }


    public void getLogin() {
        mPresenter.getLoginData("15210603710", "a00000");
    }

    public void getZhu(){
        mPresenter.getZHPData("1");
    }
}
