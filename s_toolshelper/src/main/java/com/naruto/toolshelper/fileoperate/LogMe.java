package com.naruto.toolshelper.fileoperate;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.naruto.toolshelper.BuildConfig;
import com.naruto.toolshelper.datatypetrans.ODateTime;
import com.naruto.toolshelper.SystemMe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * 使用此方法一定要先初始化init ,并设置要存放的标题setSaveMatchName
 */
public class LogMe {
    private static boolean isEnableSaveLogFull = false;
    public static void setSaveLogFull(boolean enable){
        isEnableSaveLogFull = enable;
    }
    public static boolean getSaveLogFull(){
        return isEnableSaveLogFull;
    }
    public static void showInDebug(String log){
        if(BuildConfig.DEBUG)Log.e("D",log);
    }
    public static void showInAll(String log){
        Log.e("A",log);
    }
    private static String getBaseDir(Context context) {
//        return Environment.getExternalStorageDirectory();
        return context.getExternalFilesDir(null).getAbsolutePath();
    }
    public static void putLog(final Context context,final String logName, final String logValue) {
        if(context == null)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String softInfo = context.getPackageName()+">>>"+ SystemMe.getVersionName(context)+">>>";
                    long now = System.currentTimeMillis();
                    String cachelog = "..\n\r..\r\n" + softInfo+ ODateTime.time2FromYear2Min(now)+ ":\n\r" + logName +"__" + logValue +"\n\r";
                    File file = new File(Environment.getExternalStorageDirectory(), "/MessageFiles");
                    file.mkdirs();
                    file = new File(file, "LogMeSock.txt");
                    FileOutputStream out;
                    if (file.exists()) {
                        int size = new FileInputStream(file).available();
                        int sizeKB = size/1024;
                        if(sizeKB>600){
                            out = new FileOutputStream(file, false);
                        }else {
                            out = new FileOutputStream(file, true);
                        }// （文件路径和文件的写入方式如果为真则说明文件以尾部追加的方式写入，当为假时则写入的文件覆盖之前的内容）
                    } else {
                        out = new FileOutputStream(file, false);
                    }
                    byte[] byts = cachelog.getBytes();
                    out.write(byts, 0, byts.length);
                    out.close();
                } catch (FileNotFoundException e) {
                    Log.e("OLog","OLog FileNotFoundException"+e.toString());
                    Log.e("Exception",e.toString());
                } catch (IOException e) {
                    Log.e("Exception",e.toString());
                }
            }
        }).start();
    }
    public static void putLogFull(final Context context,final String logValue) {
        if(context == null || !isEnableSaveLogFull)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(Environment.getExternalStorageDirectory(), "/MessageFiles");
                    file.mkdirs();
                    file = new File(file, "LogTest.txt");
                    FileOutputStream out;
                    if (file.exists()) {
                        int size = new FileInputStream(file).available();
                        int sizeKB = size/1024;
                        if(sizeKB>600){
                            out = new FileOutputStream(file, false);
                        }else {
                            out = new FileOutputStream(file, true);
                            OutputStreamWriter writ = null;
                            BufferedWriter writer = null;
                            try {   // new FileOutputStream(fileName, true) 第二个参数表示追加写入
                                writ = new OutputStreamWriter(out, Charset.forName("GBK"));//一定要使用gbk格式
                                writer = new BufferedWriter(writ, 1024);
                            } catch (Exception e) {
                            }
                            writer.write(logValue);
                            writer.flush();
                            writer.close();
                            //
                        }// （文件路径和文件的写入方式如果为真则说明文件以尾部追加的方式写入，当为假时则写入的文件覆盖之前的内容）
                    } else {
                        out = new FileOutputStream(file, false);
                        byte[] byts = logValue.getBytes();
                        if(out!=null)out.write(byts, 0, byts.length);
                    }
                    if(out!=null)out.close();
                } catch (FileNotFoundException e) {
                    Log.e("OLog","OLog FileNotFoundException"+e.toString());
                    Log.e("Exception",e.toString());
                } catch (IOException e) {
                    Log.e("Exception",e.toString());
                }
            }
        }).start();
    }
    // ===========================================================================
    public static void clearLogFull(final Context context) {
        if(context == null)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(Environment.getExternalStorageDirectory(), "/MessageFiles");
                    file.mkdirs();
                    file = new File(file, "LogTest.txt");
                    FileOutputStream out = new FileOutputStream(file, false);
                    byte[] byts = new byte[]{0,0};
                    out.write(byts, 0, byts.length);
                    out.close();
                } catch (FileNotFoundException e) {
                    Log.e("OLog","OLog FileNotFoundException"+e.toString());
                    Log.e("Exception",e.toString());
                } catch (IOException e) {
                    Log.e("Exception",e.toString());
                }
            }
        }).start();
    }
    // ===========================================================================
}
