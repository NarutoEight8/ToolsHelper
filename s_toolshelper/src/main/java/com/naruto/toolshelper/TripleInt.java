package com.naruto.toolshelper;

/**定长队列**/
public class TripleInt {
    public int first;
    public int center;
    public int last;
    public TripleInt(int first, int center, int last) {
        this.first = first;
        this.center = center;
        this.last = last;
    }

    public static String toString(TripleInt pair){
        if(pair == null)return "";
        return " f:"+pair.first+" c:"+pair.center+" l:"+pair.last;
    }
    public static String toString(TripleInt[] pair){
        if(pair == null)return "";
        String result = "";
        for(int i=0;i<pair.length;i++){
            result += " f:"+pair[i].first +" c:"+pair[i].center+" l:"+pair[i].last;
        }
        return result;
    }
}
