package com.example.andy.myutils.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//必须在AndroidManifest.xml文件中加入以下权限
//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//使用之前先导入包如：import com.example.andy.myutils.Utils.NetworkUtil;
//程序中直接使用如NetworkUtil.getNetWorkType(MainActivity.this)

public class NetworkUtil {
    //网络不可用
    public static final int NO_NET_WORK = 0;
    //是wifi连接
    public static final int WIFI = 1;
    //是移动网络连接
    public static final int MOBILE = 2;

    private NetworkUtil(){
        throw new UnsupportedOperationException("此类不能被实例化");
    }

    //网络是否可用
    public static boolean isNetWorkAvailable(Context context){
        boolean isAvailable = false ;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isAvailable()){
            isAvailable = true;
        }
        return isAvailable;
    }

    //获取网络类型
    public static int getNetWorkType(Context context) {
        if (!isNetWorkAvailable(context)) {
            return NetworkUtil.NO_NET_WORK;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting())
            return NetworkUtil.WIFI;
        else
            return NetworkUtil.MOBILE;
    }


    //是否是wifi连接
    public static boolean isWiFiConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo.getType() == manager.TYPE_WIFI ? true : false;
    }


    //是否启动移动数据
    public static boolean isMobileDataEnable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;
        isMobileDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return isMobileDataEnable;
    }


    //wifi是否连接或正在连接
    public static boolean isWifiDataEnable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;
        isWifiDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }

}
