package com.moible.bksx.xcb.retrofitmvpdemo.test;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.moible.bksx.xcb.retrofitmvpdemo.R;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.ReturnDataBean;
import com.moible.bksx.xcb.retrofitmvpdemo.ui.base.BaseActivity;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenterImpl> implements TestContract.View {

    @BindView(R.id.btnTest)
    Button btnTest;

    @Override
    public void initView() {
      //  btnTest = findViewById(R.id.btnTest);

    }

    @Override
    public void initEvent() {
        btnTest.setOnClickListener(v -> getMovieListData());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new TestPresenterImpl(mContext);
    }

    @Override
    public void showError(BaseResultBean resultBean) {
        //错误处理
        handleError(resultBean);
    }
    int count = 0;
    @Override
    public void setMovieListData(ReturnDataBean bean) {
        Toast.makeText(mContext, bean.toString(), Toast.LENGTH_SHORT).show();
        Log.i("TAG", "===setMovieListData: " + bean == null ? "" : bean.toString());
        count++;
        if (count == 2){
            getMovieListData();
        }
    }


    public void getMovieListData() {
        mPresenter.getMovieListData("fg",
                "cfcd208495d565ef66e7dff9f98764da",
                "106",
                "10000000001");
    }
}
