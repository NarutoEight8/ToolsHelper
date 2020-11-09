package com.nathan96169.toolshelper.dboperate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.nathan96169.toolshelper.fileoperate.LogMe;
import com.nathan96169.toolshelper.fileoperate.UtilFileSave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DBBackupRestore extends AsyncTask<String, Void, Integer> {
    private static final String COMMAND_BACKUP = "COMMAND_BACKUP";
    private static final String COMMAND_RESTORE = "COMMAND_RESTORE";
    private Context mContext;

    public DBBackupRestore(Context context) {
        this.mContext = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        File dbFile = mContext.getDatabasePath(mContext.getPackageName()+".db");
        File dbFile_shm = mContext.getDatabasePath(mContext.getPackageName()+".db-shm");// 默认路径是 /data/data/(包名)/databases/*
        File dbFile_wal = mContext.getDatabasePath(mContext.getPackageName()+".db-wal");

        File exportDir = new File(Environment.getExternalStorageDirectory(),"MessageFiles");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File backup = new File(exportDir, dbFile.getName());
        File backup_shm = new File(exportDir, dbFile_shm.getName());
        File backup_wal = new File(exportDir, dbFile_wal.getName());

        String command = params[0];
        if (command.equals(COMMAND_BACKUP)) {
            try {
                backup.createNewFile();
                fileCopy(dbFile, backup);
                fileCopy(dbFile_shm, backup_shm);
                fileCopy(dbFile_wal, backup_wal);
                LogMe.showInDebug("BACKUP OK");
            } catch (Exception e) {
                LogMe.showInDebug("BACKUP FAIL:"+e.toString());
            }
        } else if (command.equals(COMMAND_RESTORE)) {
            try {
                UtilFileSave.RecursionDeleteFile(dbFile);
                fileCopy(backup, dbFile);
                fileCopy(backup_shm, dbFile_shm);
                fileCopy(backup_wal, dbFile_wal);
                LogMe.showInDebug("RESTORE OK");
            } catch (Exception e) {
                LogMe.showInDebug("RESTORE FAIL:"+e.toString());
            }
        }
        return 0;
    }

    private void fileCopy(File dbFile, File backup) throws IOException {
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

    // 数据恢复
    public static void dataRecover(Context context) {
        new DBBackupRestore(context).execute(COMMAND_RESTORE);
    }

    // 数据备份
    public static void dataBackup(Context context) {
        new DBBackupRestore(context).execute(COMMAND_BACKUP);
    }
}