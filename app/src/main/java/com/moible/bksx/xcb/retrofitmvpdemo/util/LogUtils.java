package com.moible.bksx.xcb.retrofitmvpdemo.util;

import android.util.Log;

/**
 * Created by burro on 2017/8/13.
 * 日志打印工具类
 */
public class LogUtils {

    public static boolean LOG_FLAG = true;  //是否打印日志开关

    public static void i(String tag, String content) {
        if (checkFlag(tag,content)) Log.i(tag, content);
    }

    public static void v(String tag, String content) {
        if (checkFlag(tag,content)) Log.v(tag, content);
    }

    public static void e(String tag, String content) {
        if (checkFlag(tag,content)) Log.e(tag, content);
    }

    public static void d(String tag, String content) {
        if (checkFlag(tag,content)) Log.d(tag, content);
    }
    public static void w(String tag, String content) {
        if (checkFlag(tag,content)) Log.w(tag, content);
    }
    public static void e(String tag, String content, Exception e) {
        if (checkFlag(tag,content)) Log.e(tag, content, e);
    }
    //检查目的是防止Log的内容为空，报错
    private static boolean checkFlag(String tag,String content){
        return LOG_FLAG&&tag!=null&&content!=null;
    }
}
