package com.nathan96169.toolshelper.datatype;

/**定长队列**/
public class PairKeyValueSelectArr {
    public String key="";
    public String value;
    public String[] selectArr;

    public PairKeyValueSelectArr(String key, String value,String[] selectArr) {
        this.key = key;
        this.value = value;
        this.selectArr = selectArr;
    }
    public void setValue(String value){
        this.value = value;
    }
    public static PairKeyValueSelectArr from(String key, String value,String[] selectArr) {
        return new PairKeyValueSelectArr(key,value,selectArr);
    }
}
