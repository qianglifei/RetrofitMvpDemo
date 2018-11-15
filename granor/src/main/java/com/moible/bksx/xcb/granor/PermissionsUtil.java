package com.moible.bksx.xcb.granor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import java.util.HashMap;



public class PermissionsUtil {
    private static HashMap<String,PermissionListener> listenerHashMap = new HashMap<>();
    private static final String TAG = "PermissionGrantor";

    /**
     *
     * 用户动态申请权限，放用户拒绝时，会自动弹出一个提示Dialog 是否进行授权，同意的话进入系统授权界面
     * @param context
     * @param permissionListener
     * @param permission
     *
     */
    public static void requestPermission(@NonNull Context context, @NonNull PermissionListener permissionListener, @NonNull String... permission){
        requestPermission(context,permissionListener,permission,true,null);
    }

    /**
     * 动态的请求权限，当申请的权限被拒绝时是否弹出提示Dialog
     * @param context
     * @param permissionListener
     * @param permission  将要申请的权限
     * @param isShowTip   是否弹出提示
     * @param tipInfo    提示信息
     */
    public static void requestPermission(@NonNull Context context,@NonNull PermissionListener permissionListener, @NonNull String[] permission,@NonNull boolean isShowTip,@Nullable PermissionTipInfo tipInfo) {
        if (permissionListener == null){
            Log.e(TAG, "Permission is Null");
            return;
        }

        if (PermissionsUtil.hasPermission(context,permission)){
            permissionListener.permissionGranted(permission);
        }else {
            if (Build.VERSION.SDK_INT < 23){
                permissionListener.permissionDenied(permission);
            }else {
                String key = String.valueOf(System.currentTimeMillis());
                listenerHashMap.put(key,permissionListener);
                Intent intent = new Intent(context,PermissionsActivity.class);
                intent.putExtra("permission",permission);
                intent.putExtra("key",key);
                intent.putExtra("isShowTip",isShowTip);
                intent.putExtra("Tip",tipInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 判断全选是否授权
     * @param context
     * @param permissions 权限数组
     * @return
     */
    public static boolean hasPermission(Context context, String[] permissions) {
        if (permissions.length == 0){
            return false;
        }

        for (String strPermission: permissions) {
            int result = PermissionChecker.checkSelfPermission(context,strPermission);
            if (result != PermissionChecker.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * 接受监听
     * @param key
     * @return
     */
    public static PermissionListener fetchListener(String key){
        return listenerHashMap.remove(key);
    }
    /**
     *
     */
    public static void goSettingActivity(@NonNull Context context){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 判断一组授权结果，是否为授权通过
     * @return
     */
    public static boolean isGranted(int... grantedResult){
        if (grantedResult.length == 0){
            return false;
        }
        for (int gResult : grantedResult){
            if (gResult != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}
