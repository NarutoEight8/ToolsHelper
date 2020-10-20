package com.naruto.dispatchersample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.naruto.toolshelper.ActivityUtils;
import com.naruto.toolshelper.SystemMe;

public class ActivityNext extends AppCompatActivity {
    private TextView txt_result,txt_name;
    private Button btn_start,btn_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_name = findViewById(R.id.txt_name);
        txt_result = findViewById(R.id.txt_result);
        btn_start = findViewById(R.id.btn_start);
        btn_switch = findViewById(R.id.btn_switch);
        txt_name.setText("Activity:"+ActivityNext.class.getSimpleName());
    }

    @Override
    protected void onDestroy() {
        Log.e("Next","onDestroy");
        super.onDestroy();
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_result.setText("结果：VersionCode" + SystemMe.getVersionCode(ActivityNext.this));
            }
        });
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ActivityUtils.startActivity(ActivityNext.this, MainActivity.class);
//                Intent intent = new Intent(MainActivity.this,ActivityNext.class);
//                startActivity(intent);
            }
        });
    }
}
