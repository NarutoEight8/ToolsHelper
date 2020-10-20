package com.naruto.toolshelper;

import android.app.Activity;
import android.content.Context;

public class GlobalInitBase {
    public static OnGetInitLinstener onGetInitLinstener;
    public interface OnGetInitLinstener{
        Context getContext();
        Activity getCurrentActivity();
        void TerminateAll();
        void onRestartSerial();
        boolean isHaveNO2();
        String getcurrentFavor();
    }
    public static void setGlobal(OnGetInitLinstener listener){
        onGetInitLinstener = listener;
    }
    public static Context getContext(){
        if(onGetInitLinstener == null)return null;
        return onGetInitLinstener.getContext();
    }
    public static Activity getCurrentActivity(){
        if(onGetInitLinstener == null)return null;
        return onGetInitLinstener.getCurrentActivity();
    }
    public static String getCurrentFAVOR(){
        if(onGetInitLinstener == null)return null;
        return onGetInitLinstener.getcurrentFavor();
    }
    public static void TerminateAll(){
        if(onGetInitLinstener != null)onGetInitLinstener.TerminateAll();
    }
    public static void onRestartSerial(){
        if(onGetInitLinstener != null)onGetInitLinstener.onRestartSerial();
    }
    public static boolean isHaveNO2(){
        if(onGetInitLinstener == null)return true;
        return onGetInitLinstener.isHaveNO2();
    }
}
