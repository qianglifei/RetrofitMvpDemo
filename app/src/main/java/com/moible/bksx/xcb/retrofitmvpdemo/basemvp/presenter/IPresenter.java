package com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter;


import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.view.IBaseView;

public interface IPresenter<T extends IBaseView>{
    //附着视图
    void attachView(T view);
    //剥离视图
    void detachView();
}
