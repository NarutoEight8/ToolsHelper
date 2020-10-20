package com.naruto.toolshelper.fileoperate;

import android.os.Environment;
import android.util.Log;

import com.naruto.toolshelper.datatypetrans.ODateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaveData {

    //默认用时间作文件名
    public static void saveFile(final String head,final String fileData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long now = System.currentTimeMillis();
                    String fileName = head+ ODateTime.time2StringName(now);
                    File file = new File(Environment.getExternalStorageDirectory(), "/MessageFiles");
                    file.mkdirs();
                    file = new File(file, fileName+".txt");
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
                    byte[] byts = fileData.getBytes();
                    out.write(byts, 0, byts.length);
                    out.close();
                    if(onFileSaveListener!=null)onFileSaveListener.onSaveOK();
                } catch (FileNotFoundException e) {
                    Log.e("Exception",e.toString());
                    if(onFileSaveListener!=null)onFileSaveListener.onSaveFail();
                } catch (IOException e) {
                    Log.e("Exception",e.toString());
                    if(onFileSaveListener!=null)onFileSaveListener.onSaveFail();
                }
            }
        }).start();
    }
    public interface OnFileSaveListener{
        void onSaveOK();
        void onSaveFail();
    }
    private static OnFileSaveListener onFileSaveListener;
    public static void setOnFileSaveListener(OnFileSaveListener listener){
        onFileSaveListener = listener;
    }
}
