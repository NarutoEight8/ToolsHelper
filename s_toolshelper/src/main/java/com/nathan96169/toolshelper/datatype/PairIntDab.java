package com.nathan96169.toolshelper.datatype;

/**配对值**/
public class PairIntDab {
    private int key;
    private double value;
    public PairIntDab(int key, double value) {
        this.key = key;
        this.value = value;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
}
