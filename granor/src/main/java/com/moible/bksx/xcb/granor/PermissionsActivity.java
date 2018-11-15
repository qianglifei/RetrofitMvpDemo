package com.moible.bksx.xcb.granor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.Serializable;

/**
 *
 */
public class PermissionsActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 64;
    private  boolean isRequireCheck;
    private String[] permission;
    private String key = "";
    private boolean isShowTip;
    private PermissionTipInfo tipInfo;

    private final String defaultTitle = "帮助";
    private final String defaultContent = "当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。";
    private final String defaultCancel = "取消";
    private final String defaultEnsure = "设置";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        if (getIntent() == null || !getIntent().hasExtra("permission")){
            finish();
            return;
        }

        isRequireCheck = true;
        permission = getIntent().getStringArrayExtra("permission");
        key = getIntent().getStringExtra("key");
        isShowTip = getIntent().getBooleanExtra("isShowTip",true);
        Serializable serializable = getIntent().getSerializableExtra("Tip");
        if (serializable == null){
            tipInfo = new PermissionTipInfo(defaultTitle,defaultContent,defaultCancel,defaultEnsure);
        }else {
            tipInfo = (PermissionTipInfo) serializable;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck){
            if (PermissionsUtil.hasPermission(this,permission)){ 
                permissionsGranted();
            }else {
                //请求权限，回调时会触发OnResume
                requestPermissions(permission);
                isRequireCheck = false;
            }
        }else {
            isRequireCheck = true;
        }
    }

    /**
     * 请求权限兼容低版本
     * @param permission
     */
    private void requestPermissions(String[] permission) {
        ActivityCompat.requestPermissions(this,permission,PERMISSION_REQUEST_CODE);
    }

    /**
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //部分手机厂商系统返回授权成功时，厂商可以拒绝，所以要用PermissionChecker做二次检查
        if (requestCode == PERMISSION_REQUEST_CODE
                && PermissionsUtil.isGranted(grantResults)
                && PermissionsUtil.hasPermission(this,permissions)){
            permissionsGranted();
        }else if (isShowTip){
            showMissingPermissionDialog();
        }else {
            permissionsDenied();
        }
    }

    private void showMissingPermissionDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(TextUtils.isEmpty(tipInfo.getTitle()) ? defaultTitle : tipInfo.getTitle());
        alert.setMessage(TextUtils.isEmpty(tipInfo.getContent()) ? defaultContent : tipInfo.getContent());
        //确认按钮
        alert.setPositiveButton(TextUtils.isEmpty(tipInfo.getEnsure()) ? defaultEnsure : tipInfo.getEnsure(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionsUtil.goSettingActivity(PermissionsActivity.this);
            }
        });
        //取消按钮
        alert.setNegativeButton(TextUtils.isEmpty(tipInfo.getCancel()) ? defaultCancel : tipInfo.getCancel(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionsDenied();
            }
        });
        alert.setCancelable(false);
        alert.show();
    }

    /**
     * 全部权限均已获取
     */
    private void permissionsGranted() {
        PermissionListener permissionListener = PermissionsUtil.fetchListener(key);
        if (permissionListener != null){
            permissionListener.permissionGranted(permission);
            finish();
        }
    }

    private void permissionsDenied() {
        PermissionListener permissionListener = PermissionsUtil.fetchListener(key);
        if (permissionListener != null){
            permissionListener.permissionDenied(permission);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionsUtil.fetchListener(key);
    }
}
