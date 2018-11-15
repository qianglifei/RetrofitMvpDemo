package com.moible.bksx.xcb.retrofitmvpdemo.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.moible.bksx.xcb.retrofitmvpdemo.R;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;
import com.moible.bksx.xcb.retrofitmvpdemo.ui.base.BaseActivity;
import com.moible.bksx.xcb.retrofitmvpdemo.ui.bottomfragment1.BottomFragment1;
import com.moible.bksx.xcb.retrofitmvpdemo.ui.bottomfragment2.BottomFragment2;
import com.moible.bksx.xcb.retrofitmvpdemo.ui.bottomfragment3.BottomFragment3;
import com.moible.bksx.xcb.retrofitmvpdemo.ui.bottomfragment4.BottomFragment4;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.layFrame)
    FrameLayout layFrame;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBotNavBar;
    private FragmentManager fm = getSupportFragmentManager();

    BottomFragment1 bottomFragment1;
    BottomFragment2 bottomFragment2;
    BottomFragment3 bottomFragment3;
    BottomFragment4 bottomFragment4;
    @Override
    public void initView() {

    }
    @Override
    public void initEvent() {
        mBotNavBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBotNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        //配置tab与之对应的fragment
        mBotNavBar.addItem(new BottomNavigationItem(R.mipmap.bottom_sy, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_jl, "简历"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_zw, "职位"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_zx, "资讯"));
        mBotNavBar.setActiveColor("#4baaeb")
                .setInActiveColor("#ff666666")
                .setBarBackgroundColor("#ECECEC");

        //设置Bottom
        mBotNavBar.setFirstSelectedPosition(0).initialise();
        //设置默认fragment
        onTabSelected(0);

        mBotNavBar.setTabSelectedListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void createPresenter() {
    }


    @Override
    public void onTabSelected(int position) {
        //开启一个Fragment的事物
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //先隐藏掉所有的Fragment,以防多个Fragment同时出现在界面上
        hideFragment(fragmentTransaction);
        switch (position) {
            case 0: {
                if (bottomFragment1 == null) {
                    bottomFragment1 = new BottomFragment1();
                    fragmentTransaction.add(R.id.layFrame, bottomFragment1);
                } else {
                    fragmentTransaction.show(bottomFragment1);
                }
            }
            break;
            case 1: {
                if (bottomFragment2 == null) {
                    bottomFragment2 = new BottomFragment2();
                    fragmentTransaction.add(R.id.layFrame, bottomFragment2);
                } else {
                    fragmentTransaction.remove(bottomFragment2);
                    bottomFragment2 = new BottomFragment2();
                    fragmentTransaction.add(R.id.layFrame, bottomFragment2);
                }

            }
            break;
            case 2: {
                if (bottomFragment3 == null) {
                    bottomFragment3 = new BottomFragment3();
                    fragmentTransaction.add(R.id.layFrame, bottomFragment3);
                } else {
                    fragmentTransaction.remove(bottomFragment3);
                    bottomFragment3 = new BottomFragment3();
                    fragmentTransaction.add(R.id.layFrame, bottomFragment3);
                }
            }
            break;
            case 3: {
                if (bottomFragment4 == null) {
                    bottomFragment4 = new BottomFragment4();
                    fragmentTransaction.add(R.id.layFrame, bottomFragment4);
                } else {
                    fragmentTransaction.show(bottomFragment4);
                }

            }
            break;
            default:
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }



    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        BottomFragment1 fragment1 = new BottomFragment1();
        BottomFragment2 fragment2 = new BottomFragment2();
        BottomFragment3 fragment3 = new BottomFragment3();
        BottomFragment4 fragment4 = new BottomFragment4();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);

        return fragments;
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (bottomFragment1 != null) {
            fragmentTransaction.hide(bottomFragment1);
        }
        if (bottomFragment2 != null) {
            fragmentTransaction.hide(bottomFragment2);
        }
        if (bottomFragment3 != null) {
            fragmentTransaction.hide(bottomFragment3);
        }
        if (bottomFragment4 != null) {
            fragmentTransaction.hide(bottomFragment4);
        }
    }

    @Override
    public void showError(BaseResultBean resultBean) {

    }
}
