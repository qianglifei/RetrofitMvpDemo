package com.moible.bksx.xcb.retrofitmvpdemo.ui.login;

import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter.IPresenter;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.view.IBaseView;

import okhttp3.ResponseBody;

public class LoginContract {
    public interface View extends IBaseView {
        void setLoginData(ResponseBody bean);
        void setZHPData(ResponseBody we);
    }

    public interface Presenter extends IPresenter<LoginContract.View> {
        void getLoginData(String yhm,String mm);
        void getZHPData(String pageNum);
    }
}
