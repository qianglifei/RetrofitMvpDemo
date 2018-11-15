package com.moible.bksx.xcb.retrofitmvpdemo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashSet;
import java.util.Set;

public class BaseApplication extends Application {

    private static BaseApplication mBaseApplication;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    public static Context mContext;
    private Set<Activity> allActivities;
    public final static String  APP_PACKET_NAME = "com.moible.bksx.xcb.retrofitmvpdemo";
    public BaseApplication(){

    }

    public BaseApplication(Set<Activity> allActivities) {
        this.allActivities = allActivities;
    }

    /**
     * 懒汉式（线程安全，同步方法）【不被推荐使用】
     * @return
     */
    public static synchronized BaseApplication getInstance(){
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        mBaseApplication = this;
        //获取屏幕的大小
        getScreenSize();
        //CrashHandler.getInstance().init(this);
    }

    private void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);

        DIMEN_RATE = displayMetrics.density / 1.0F;
        DIMEN_DPI = displayMetrics.densityDpi;
        SCREEN_WIDTH = displayMetrics.widthPixels;
        SCREEN_HEIGHT = displayMetrics.heightPixels;

        if (SCREEN_WIDTH > SCREEN_HEIGHT){
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    /**
     * 初始化字体，默认字体，不随着系统的改变而改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration newConfig = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        if (resources != null && newConfig.fontScale != 1) {
            newConfig.fontScale = 1;
            if (Build.VERSION.SDK_INT >= 17) {
                Context configurationContext = createConfigurationContext(newConfig);
                resources = configurationContext.getResources();
                displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale;
            } else {
                resources.updateConfiguration(newConfig, displayMetrics);
            }
        }
        return resources;
    }

    /**
     * 添加Activity
     * @param activity
     */
    public void addActivity(Activity activity){
        if (allActivities == null){
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }

    /**
     * 删除Activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        if (allActivities != null){
            allActivities.remove(activity);
        }
    }

    /**
     * 退出app
     */
    public void exitApp(){
        if (allActivities != null){
            synchronized (allActivities){
                for (Activity act: allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    /**
     * 第三方框架的集成。。。。
     */
}
