package com.example.andy.myutils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;


//使用之前先导入包如：import com.example.andy.myutils.Utils.SystemSettingUtil;
//程序中直接使用如SystemSettingUtil.GoSettingDEVELOPMENT(this);

public class SystemSettingUtil {

    private SystemSettingUtil(){
        throw new UnsupportedOperationException("此类不能被实例化");
    }

    //跳转系统设置页面
    public static void GoSettingSETTINGS(Activity activity){
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转APN设置页面
    public static void GoSettingAPN(Activity activity){
        Intent intent = new Intent(Settings.ACTION_APN_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转定位设置页面
    public static void GoSettingLOCATION(Activity activity){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转蓝牙设置页面
    public static void GoSettingBLUETOOTH(Activity activity){
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转日期和时间设置页面
    public static void GoSettingDATE(Activity activity){
        Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转声音设置页面
    public static void GoSettingSOUND(Activity activity){
        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转显示设置页面
    public static void GoSettingDISPLAY(Activity activity){
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转语言和输入法设置页面
    public static void GoSettingINPUT(Activity activity){
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转开发者选项设置页面
    public static void GoSettingDEVELOPMENT(Activity activity){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转字幕设置页面
    public static void GoSettingCAPTIONING(Activity activity){
        Intent intent = new Intent(Settings.ACTION_CAPTIONING_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转打印设置页面
    public static void GoSettingPRINT(Activity activity){
        Intent intent = new Intent(Settings.ACTION_PRINT_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转设置WIFI页面
    public static void GoSettingWIFI(Activity activity){
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        activity.startActivity(intent);
    }

    //跳转设置移动数据页面
    public static void GoSettingMOBILE(Activity activity){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }
}
