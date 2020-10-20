package com.naruto.toolshelper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

public class SystemMe {
    public static String UTF8 = "utf-8";
    private static String versionName = "";

    /**
     * context.getPackageName(); 取包名
     * BuildConfig.APPLICATION_ID 取Module名
     *
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static String getUUID(Context context) {
        String data = ODBHelper.getInstance().queryCommonInfo("UUID");
        String UUID_USE;
        if (data == null || data.length() == 0) {
            UUID uuid = UUID.randomUUID();
            UUID_USE = uuid.toString();//6b003d48-1dc2-41ca-8f0c-4ff5104531e4
//            Log.e("getUUID", "UI 生成了一个新的UUID:" + UUID_USE);//9d50c7e9-0ffd-4b34-9a9e-47e01b18fddc
            ODBHelper.getInstance().changeCommonInfo("UUID", UUID_USE);
        } else {
            UUID_USE = data;
        }
        return UUID_USE;
    }
    //系统的可用内存/总内存
    public static String getMemInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        double pencent = (double) mi.availMem / mi.totalMem;
        String avi = Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
        String tol = Formatter.formatFileSize(context, mi.totalMem);// 将获取的内存大小规格化
        return "Mem:"+(int)(pencent*100)+"% "+avi+"/"+tol;
    }
    /**开发板需要同时判读Mac WifiState 读设备号*/
    private static String mac = "";
    public static String getMac(Context context) {
        if(!TextUtils.isEmpty(mac))return mac;
        String macAddress1 = ODBHelper.getInstance().queryCommonInfo("MacAddress");
        if(!TextUtils.isEmpty(macAddress1)){
            mac = macAddress1;
            return mac;
        }
        try {
            String macAddress = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//5.0以下
                WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifi.getConnectionInfo();//android.permission.ACCESS_WIFI_STATE
                macAddress = wifiInfo.getMacAddress();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {//android 6~7
                macAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//>android 7
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return null;
                    }
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }
                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    macAddress = res1.toString();
                }
            }
            if(!TextUtils.isEmpty(macAddress)){
                mac = macAddress;
                return mac;
            }else return null;
        }catch (Exception e){
            return null;
        }
    }




    /**
     * 判断网络连接
     **/
    public static Boolean isNetworkOn(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return false;
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) return false;
        return true;
    }

    public static boolean isWifiOn(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return false;
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (wifi == NetworkInfo.State.CONNECTED)return true;
        return false;
    }
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                if (ipAddress == 0) return "未连接wifi";
                return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."+ (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            return "当前无网络连接,请在设置中打开网络";
        }
        return "IP获取失败";
    }

}
