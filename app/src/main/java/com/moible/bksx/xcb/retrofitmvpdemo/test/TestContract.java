package com.moible.bksx.xcb.retrofitmvpdemo.test;

import com.moible.bksx.xcb.retrofitmvpdemo.bean.ReturnDataBean;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.presenter.IPresenter;
import com.moible.bksx.xcb.retrofitmvpdemo.basemvp.view.IBaseView;

/**协议类，定制mvp各层接口和实现方法
 * Contract:d单词意思为契约 协议
 * 接口View内定义实现view内所需方法
 * 接口Presenter 定义实现presenter内所需的方法
 *
 */
public class TestContract {
    public interface View extends IBaseView {
        void setMovieListData(ReturnDataBean bean);
    }

    public interface Presenter extends IPresenter<View> {
        void getMovieListData(String yhm,String mm,String version,String imsi);
    }
}
