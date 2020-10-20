package com.naruto.toolshelper;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String getStr(TextView view) {
        return view.getText().toString().trim();
    }

    public static String getStr(EditText view) {
        return view.getText().toString().trim();
    }

    public static String getStr(Button view) {
        return view.getText().toString().trim();
    }

    /**
     * 加头尾
     **/
    public static String longStrLR(String str, String add, int length) {
        int len = 0;
        for (int i = 0; i < str.length(); i++) {
            String A = str.substring(i,i+1);
            int num = getStrType(A);
            if(num == 2)len+=2;
            else len+=1;
        }
        int half = (length - len) / 2;
        if(len%2 == 1)str+=add;
        for (int i = 0; i < half; i++) {
            str = add + str + add;
        }
        return str;
    }

    /**
     * 是什么字符 0 数字 1字母 2汉字
     **/
    public static int getStrType(String txt) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        if (m.matches()) return 0;//输入的是数字
        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(txt);
        if (m.matches()) return 1;//输入的是字母
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(txt);
        if (m.matches()) return 2;//输入的是汉字
        return -1;
    }
}
