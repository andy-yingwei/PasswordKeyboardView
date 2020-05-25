package com.example.andy.myutils.Utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//必须在AndroidManifest.xml文件中加入以下权限
//允许APP访问精确地理位置<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
//允许APP访问大概地理位置<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
public class LocationForApiUtil {

    private static LocationForApiUtil locationForApiUtil;
    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private double latitude = 0;            //维度
    private double longitude;           //经度
    private String countryName;         //国家名称
    private String cityName;            //城市名称
    private String streetName;          //街道名称

    private LocationForApiUtil() {

    }

    public static LocationForApiUtil getInstance() {
        if (locationForApiUtil == null) {
            locationForApiUtil = new LocationForApiUtil();
        }
        return locationForApiUtil;
    }


    public void OpenGPS(final Context mContext) {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为了获取定位服务，请先打开GPS");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //界面跳转
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });
            dialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();
        }
    }

    private void SetCriteria(final Context mContext) {
        Criteria criteria = new Criteria();
        // Criteria是一组筛选条件
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置定位精准度
        criteria.setAltitudeRequired(true);
        //是否要求海拔
        criteria.setBearingRequired(false);
        //是否要求方向
        criteria.setCostAllowed(false);
        //是否要求收费
        criteria.setSpeedRequired(true);
        //是否要求速度
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        //设置电池耗电要求
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        //设置方向精确度
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        //设置速度精确度
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        //设置水平方向精确度
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        //设置垂直方向精确度
        // 获取最佳服务对象
        String provider = locationManager.getBestProvider(criteria, true);
        //locationManager.getLastKnownLocation(provider);
        if (provider != null) {
            //Log.d("MyLog", "您使用的定位方式是:" + provider);
            //Toast.makeText(mContext, "您使用的定位方式是:" + provider, Toast.LENGTH_SHORT).show();
        }
    }



    private void SetLocationListener(final Context mContext){
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 收到位置信息
                latitude = location.getLatitude();
                longitude =  location.getLongitude();
                //Log.d("MyLog", "latitude" + latitude);
                //Log.d("MyLog", "longitude" + longitude);
                GetGeocoder(mContext);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle arg2) {
                // 定位提供程序状态发生改变(国内只考虑GPS，而GPS一般不会回调这个方法)
            }

            @Override
            public void onProviderEnabled(String provider) {
                // GPS打开
            }

            @Override
            public void onProviderDisabled(String provider) {
                // GPS 关闭
            }
        };
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null);
    }

    private void GetGeocoder(Context mContext) {
        Geocoder mGeocoder = new Geocoder(mContext);
        boolean flag = Geocoder.isPresent();
        if (flag) {
            //Log.d("MyLog", "您的手机内置了地理编码服务！");
            List<Address> mAddressList = null;
            try {
                mAddressList = mGeocoder.getFromLocation(latitude, longitude, 1);//得到的位置可能有多个当前只取其中一个
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mAddressList != null && mAddressList.size() > 0) {
                Address mAddress = mAddressList.get(0);
                countryName = mAddress.getCountryName();             //得到国家名称
                cityName = mAddress.getLocality();                   //得到城市名称
                for (int i = 0; mAddress.getAddressLine(i) != null; i++) {
                    streetName = mAddress.getAddressLine(i);//得到周边信息，包括街道等，i=0，得到街道名称
                }
            }
        }else {
            Log.d("MyLog", "您的手机没有内置地理编码服务，无法获得当前定位！");
            //Toast.makeText(mContext, "您的手机没有内置地理编码服务，无法获得当前定位！", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetLocation(Context mContext){
        OpenGPS(mContext);
        SetCriteria(mContext);
        SetLocationListener(mContext);
        Log.d("MyLog", "latitude"+latitude);
        Log.d("MyLog", "longitude"+longitude);
    }


    public String getCountryName(){
        Log.d("MyLog", "国家：" + countryName);
        return countryName;
    }

    public String getCityName(){
        Log.d("MyLog", "城市：" + cityName);
        return cityName;
    }

    public String getStreetName(){
        Log.d("MyLog", "街道：" + streetName);
        return streetName;
    }

    /**
     private class MyLocationListener implements LocationListener {
     //当位置发生改变后回调，经纬度相关信息存在Location里面
     public void onLocationChanged(Location location) {
     latitude = location.getLatitude();
     longitude =  location.getLongitude();
     }

     //当provider状态改变时回调
     public void onStatusChanged(String provider, int status, Bundle extras) {
     Log.d("MyLog", "onStatusChanged" + status);
     }

     //当provider可用时被触发
     public void onProviderEnabled(String provider) {
     Log.d("MyLog", "onProviderEnabled");
     }

     //当provider不可用时被触发
     public void onProviderDisabled(String provider) {
     Log.d("MyLog", "onProviderDisabled");
     }

     }
     **/
}



