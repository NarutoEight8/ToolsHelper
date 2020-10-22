package com.nathan96169.toolshelper.datatype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**定长队列**/
public class PairStrInt {
    public String str;
    public int num;
    public PairStrInt(String str, int num) {
        this.str = str;
        this.num = num;
    }
    public static PairStrInt fromArr(Object[] objects){
        PairStrInt result = new PairStrInt((String) objects[0],(int)objects[1]);
        return result;
    }
    public PairStrInt(Object objects){
        this.str = "aa";
        this.num = 1;
    }
    private void sampleA(){
        //A
        PairStrInt[] li= {new PairStrInt("aa",1),new PairStrInt("aa",1),new PairStrInt("aa",1)};
        List<PairStrInt> arr = Arrays.asList(li);
        //B
        List<PairStrInt> ar = new ArrayList<>();
        ar.add(new PairStrInt("aa",1));
        ar.add(new PairStrInt("aa",1));
        ar.add(new PairStrInt("aa",1));
        //C
        String[] sarr = {"a","b","c","d"};
//        PairStrInt pairs = PairStrInt.fromArr({"aa",1});
//        PairStrInt[] lili= {{"aa",1},{"aa",1}};
//        List<PairStrInt> arrr = Arrays.asList(lili);
    }
}
