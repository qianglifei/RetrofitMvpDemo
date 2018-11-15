package com.moible.bksx.xcb.retrofitmvpdemo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.moible.bksx.xcb.retrofitmvpdemo.BuildConfig;
import com.moible.bksx.xcb.retrofitmvpdemo.application.BaseApplication;

import java.io.File;

/**
 * Created by burro on 2017/8/13.
 * 文件相关处理类
 */
public class FileUtils {
    private static FileUtils instance;
    private static String APP_SDCARD_PATH;
    //private static String APP_BASE_PATH = "Android/data/" + ApplicationParams.APP_PACKET_NAME + "/";
    private static String APP_BASE_PATH = "Android/data/" + BaseApplication.APP_PACKET_NAME + "/";
    private static String APP_LOG_PATH = "logCrash/";
    private static String IMAGE_CACHE_PATH = "imageCache/";
    private static String HTTP_CACHE_PATH = "httpCache/";

    private FileUtils() {
        init();
    }

    //初始化文件夹
    public static void init() {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            APP_SDCARD_PATH = Environment.getExternalStorageDirectory() + "/";//获取跟目录
        }else {
            APP_SDCARD_PATH = Environment.getRootDirectory() + "/";
        }

        File file = new File(APP_SDCARD_PATH + APP_BASE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(APP_SDCARD_PATH + APP_BASE_PATH + IMAGE_CACHE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(APP_SDCARD_PATH + APP_BASE_PATH + APP_LOG_PATH);
        if (!file.exists()) {
           file.mkdir();
        }
        file = new File(APP_SDCARD_PATH + APP_BASE_PATH + HTTP_CACHE_PATH);
        if (!file.exists()) {
          file.mkdir();
        }
    }

    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    public String getAppBasePath() {
        return APP_SDCARD_PATH + APP_BASE_PATH+ APP_BASE_PATH;
    }

    public String getAppLogPath() {
        return APP_SDCARD_PATH + APP_BASE_PATH+ APP_LOG_PATH;
    }

    public String getImageCachePath() {
        return APP_SDCARD_PATH+ APP_BASE_PATH + IMAGE_CACHE_PATH;
    }
    public String getCrashLogPath(){
        return APP_SDCARD_PATH + APP_BASE_PATH+ APP_LOG_PATH;
    }
    public String getHttpCachePath() {
        return APP_SDCARD_PATH+ APP_BASE_PATH + HTTP_CACHE_PATH;
    }

    //打开文件
    public static void viewFile(final Context context, final String filePath) {
        final Uri mUri;
        String type = getMimeType(filePath);
        //解决安卓7.0的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    new File(filePath));
        } else {
            mUri = Uri.fromFile(new File(filePath));
        }
        if (!TextUtils.isEmpty(type) && !TextUtils.equals(type, "*/*")) {
            /* 设置intent的file与MimeType */
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(mUri, type);
            context.startActivity(intent);
        } else {
            // unknown MimeType ，可在数组内增加需要的类型，与dialog内的内容一致,dialog根据情况设置风格。
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("选择文本类型");

            CharSequence[] menuItemArray = new CharSequence[]{
                    "文本",
                   "图像"};


            //CharSequence[] menuItemArray = new CharSequence[] {"文本", "图像"};
            dialogBuilder.setItems(menuItemArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String selectType = "*/*";
                    switch (which) {
                        case 0:
                            selectType = "text/plain";
                            break;
                        case 1:
                            selectType = "image/*";
                            break;
                    }
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(mUri, selectType);
                    context.startActivity(intent);
                }
            });
            dialogBuilder.show();
        }
    }
    //获取MimeType
    private static String getMimeType(String filePath) {
        int dotPosition = filePath.lastIndexOf('.');
        if (dotPosition == -1)
            return "*/*";

        String ext = filePath.substring(dotPosition + 1, filePath.length()).toLowerCase();
        String mimeType = MimeUtils.guessMimeTypeFromExtension(ext);

        return mimeType != null ? mimeType : "*/*";
    }
}