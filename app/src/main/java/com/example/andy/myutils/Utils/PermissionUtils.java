package com.example.andy.myutils.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.andy.myutils.MainActivity;

import java.util.ArrayList;
import java.util.List;

//android6.0后,仅在manifest.xml清单中配置相关权限时,sdk为23及以上,敏感权限需要动态申请后才能使用

public class PermissionUtils {


        private final int mRequestCode = 100;               //权限请求码
        public static boolean showSystemSetting = true;     //是否显示系统设置
        private static PermissionUtils permissionUtils;
        private static String passPermissonsText;
        private static String stopPermissonsText;

        private PermissionUtils() {

        }


        public static PermissionUtils getInstance() {
            if (permissionUtils == null) {
                permissionUtils = new PermissionUtils();
            }
            return permissionUtils;
        }

        public void setPassPermissonsText(String string){
            passPermissonsText = string;
        }

        public void setStopPermissonsText(String string){
            stopPermissonsText = string;
       }

        private void shwoPassPermissonsText(Activity context){
            if (passPermissonsText != null){
                Toast.makeText(context, passPermissonsText, Toast.LENGTH_SHORT).show();
            }
        }

        private void shwoStopPermissonsText(Activity context){
            if (stopPermissonsText != null){
                Toast.makeText(context, stopPermissonsText, Toast.LENGTH_SHORT).show();
            }
        }

        //检查授权
        public void chekPermissions(Activity context, String[] permissions) {

            if (Build.VERSION.SDK_INT < 23) {//6.0才用动态权限
                shwoPassPermissonsText(context);
                return;
            }

            List<String> mPermissionList = new ArrayList<>();
            //逐个判断你要的权限是否已经通过
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);//添加还未授予的权限
                }
            }

            //申请权限
            if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
                ActivityCompat.requestPermissions(context, permissions, mRequestCode);
            } else {
                //说明权限都已经通过，可以做你想做的事情去
                shwoPassPermissonsText(context);
                return;
            }

        }

        //请求权限后回调的方法
        //参数： requestCode  是我们自己定义的权限请求码
        //参数： permissions  是我们请求的权限名称数组
        //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限

        public void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            boolean hasPermissionDismiss = false;//有权限没有通过
            if (mRequestCode == requestCode) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == -1) {
                        hasPermissionDismiss = true;
                    }
                }
                //如果有权限没有被允许
                if (hasPermissionDismiss) {
                    if (showSystemSetting) {
                        showSystemPermissionsSettingDialog(context);//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                    } else {
                        shwoStopPermissonsText(context);
                    }
                } else {
                    //全部权限通过，可以进行下一步操作。。。
                    shwoPassPermissonsText(context);
                }
            }

        }


        /**
         * 不再提示权限时的展示对话框
         */
        AlertDialog mPermissionDialog;
        private void showSystemPermissionsSettingDialog(final Activity context) {
            final String mPackName = context.getPackageName();
            if (mPermissionDialog == null) {
                mPermissionDialog = new AlertDialog.Builder(context)
                        .setMessage("已禁用权限，请手动授予")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelPermissionDialog();

                                Uri packageURI = Uri.parse("package:" + mPackName);
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                context.startActivity(intent);
                                context.finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //关闭页面或者做其他操作
                                cancelPermissionDialog();
                                //mContext.finish();
                                shwoStopPermissonsText(context);
                            }
                        })
                        .create();
            }
            mPermissionDialog.show();
        }

        //关闭对话框
        private void cancelPermissionDialog() {
            if (mPermissionDialog != null) {
                mPermissionDialog.cancel();
                mPermissionDialog = null;
            }

        }

    }