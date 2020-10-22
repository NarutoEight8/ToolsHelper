package com.naruto.toolshelper.dboperate;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.naruto.toolshelper.GlobalInitBase;
import com.naruto.toolshelper.PermissionUtil;
import com.naruto.toolshelper.fileoperate.LogMe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DBBackupRestore {
    public static final String BACKUP_DIC =  new File(Environment.getExternalStorageDirectory(), "/MessageFiles/").getAbsolutePath();
    @SuppressLint("StaticFieldLeak")

    public static void doBackup(String DBNAME,String BACKUPNAME) throws Exception {
        PermissionUtil.checkExternalStoragePermission(GlobalInitBase.getCurrentActivity());
        File dbFile = GlobalInitBase.getContext().getDatabasePath(DBNAME);// 默认路径是 /data/data/(包名)/databases/*
        File exportDir = new File(BACKUP_DIC);//    /sdcard/Never Forget/Backup
        exportDir.mkdirs();
        File backup = new File(exportDir, BACKUPNAME);//备份文件与原数据库文件名一致
        backup.createNewFile();
        fileCopy(dbFile, backup);//数据库文件拷贝至备份文件
        LogMe.showInDebug( "backup ok! 备份文件名："+backup.getAbsolutePath());
    }
    public static void doRestore(String DBNAME,String BACKUPFILEPATH) throws Exception{
        PermissionUtil.checkExternalStoragePermission(GlobalInitBase.getCurrentActivity());
        File dbFile = GlobalInitBase.getContext().getDatabasePath(DBNAME);// 默认路径是 /data/data/(包名)/databases/*
        File restoreFile = new File(BACKUPFILEPATH);//备份文件与原数据库文件名一致
        fileCopy(restoreFile, dbFile);//备份文件拷贝至数据库文件
        LogMe.showInDebug( "restore success! 数据库文件名："+dbFile.getName());
    }

    private static void fileCopy(File dbFile, File backup) throws IOException {
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
}
