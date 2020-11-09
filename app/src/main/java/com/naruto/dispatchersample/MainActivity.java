package com.naruto.dispatchersample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.naruto.dispatchersample.databinding.ActivityMainBinding;
import com.nathan96169.toolshelper.GlobalInitBase;
import com.nathan96169.toolshelper.GlobalInitBase.OnGetInitLinstener;
import com.nathan96169.toolshelper.PermissionUtil;
import com.nathan96169.toolshelper.dboperate.ODBHelper;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtName.setText("Activity:"+MainActivity.class.getSimpleName());
        GlobalInitBase.setGlobal(new OnGetInitLinstener() {
            @Override
            public Context getContext() {
                return MainActivity.this;
            }

            @Override
            public Activity getCurrentActivity() {
                return MainActivity.this;
            }

            @Override
            public void TerminateAll() {

            }

            @Override
            public void onRestartSerial() {

            }

            @Override
            public boolean isHaveNO2() {
                return false;
            }

            @Override
            public String getcurrentFavor() {
                return null;
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.e("Main","onDestroy");
        super.onDestroy();
    }

    private int preNum = 0;
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        PermissionUtil.checkExternalStoragePermission(MainActivity.this);
        binding.btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ODBHelper.getInstance().queryCommonInfo("preNum");
                if(!TextUtils.isEmpty(str))preNum = Integer.valueOf(str)+1;
                binding.txtResult.setText("当前数值："+preNum);
            }
        });
        binding.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackupTask.dataBackup(MainActivity.this);
            }
        });
        binding.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackupTask.dataRecover(MainActivity.this);
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ODBHelper.getInstance().changeCommonInfo("preNum",""+preNum);
//                finish();
//                ActivityUtils.startActivity(MainActivity.this, ActivityNext.class);
//                Intent intent = new Intent(MainActivity.this,ActivityNext.class);
//                startActivity(intent);
            }
        });
    }

}
