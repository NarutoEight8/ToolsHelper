package com.naruto.toolshelper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

/**
 <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 */
public class LocationUtil {
    public static final int MY_PERMISSIONS_REQUEST_CODE = 10001;
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;

    public static boolean checkLocationPermission(Context context) {  //检查获取位置权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != GRANTED) {
            ActivityCompat.requestPermissions((Activity) context
                    , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}
                    ,MY_PERMISSIONS_REQUEST_CODE);
        } else {//权限已经允许
            return true;
        }
        return false;
    }

    public static void getCurrentLocation(){
        Context context = GlobalInitBase.getContext();
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 检查是否有相关定位权限
        if(!checkLocationPermission(context)){
            return;
        }
    }
    /**
     *  gps 提供器
     * @param locationManager
     * @return
     */
    public static String getGPSProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        // gps定位
        if(prodiverlist.contains(LocationManager.GPS_PROVIDER))
            return LocationManager.GPS_PROVIDER;
        return null;
    }

}
