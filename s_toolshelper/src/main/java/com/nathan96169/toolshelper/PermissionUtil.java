package com.nathan96169.toolshelper;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public static final int MY_PERMISSIONS_REQUEST_CODE = 10001;
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;

    public static boolean checkAudioPermission(Context context) {  //检查麦克风权限 ,写入文件，读出文件权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != GRANTED) {
            ActivityCompat.requestPermissions((Activity) context
                    , new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                    ,MY_PERMISSIONS_REQUEST_CODE);
        } else {//权限已经允许
            return true;
        }
        return false;
    }

    public static boolean checkCameraPermission(Context context) {  //检查拍照权限 ,写入文件，读出文件权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != GRANTED) {
            ActivityCompat.requestPermissions((Activity) context
                    , new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                    ,MY_PERMISSIONS_REQUEST_CODE);
        } else {//权限已经允许
            return true;
        }
        return false;
    }


    public static boolean checkExternalStoragePermission(Context context) {  //检查写入文件，读出文件权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != GRANTED) {
            ActivityCompat.requestPermissions((Activity) context
                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                    ,MY_PERMISSIONS_REQUEST_CODE);
        } else {//权限已经允许
            return true;
        }
        return false;
    }


    public static boolean checkBluePermission(Activity context) {  //检查蓝牙权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != GRANTED
                ||ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) != GRANTED
                ||ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != GRANTED) {

            ActivityCompat.requestPermissions( context
                    , new String[]{ Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}
                    ,MY_PERMISSIONS_REQUEST_CODE);
            return false;
        } else {//权限已经允许
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter == null)return false;//没有蓝牙
            if(!bluetoothAdapter.isEnabled()){
                openBlueTooth(context);
                return false;
            }
            return true;
        }
    }
    public static void openBlueTooth(Activity context) {
//        bluetoothAdapter.enable();
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);//[1<->3600] s ，为 0 时，表示打开 Bluetooth 之后一直可见，设置小于0或者大于3600 时，系统会默认为 120s。
        context.startActivity(intent);
    }

    public static boolean checkContactsPermission(Context context) {  //检查获取联系人权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != GRANTED) {
            ActivityCompat.requestPermissions((Activity) context
                    , new String[]{Manifest.permission.READ_CONTACTS}
                    ,MY_PERMISSIONS_REQUEST_CODE);
        } else {//权限已经允许
            return true;
        }
        return false;
    }

    public static boolean checkPhonePermission(Context context) {  //检查获取电话权限

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != GRANTED) {
            ActivityCompat.requestPermissions((Activity) context
                    , new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.CALL_PHONE}
                    ,MY_PERMISSIONS_REQUEST_CODE);
        } else {//权限已经允许
            return true;
        }
        return false;
    }
    //=======================================
}