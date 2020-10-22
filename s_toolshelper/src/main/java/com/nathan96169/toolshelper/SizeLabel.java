package com.nathan96169.toolshelper;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import org.xml.sax.XMLReader;

/**
 * TextView设置Gravity为center后对Html文本不生效，只能左右居中，不能上下居中
 */

public class SizeLabel implements Html.TagHandler {
    private int size;    
    private int startIndex = 0;    
    private int stopIndex = 0;
    private Context context;
    public SizeLabel(Context context, int size) {
        this.context = context;
        this.size = size;    
    }    

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
       if(tag.toLowerCase().equals("size")) {            
           if(opening){                
               startIndex = output.length();            
           }else{                
               stopIndex = output.length();                
               output.setSpan(new AbsoluteSizeSpan(dip2px(size)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
          }        
       }    
    }

   private int dip2px(float dpValue) {
       final float scale = context.getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
   }
}