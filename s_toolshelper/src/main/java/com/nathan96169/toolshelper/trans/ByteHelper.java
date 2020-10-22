package com.nathan96169.toolshelper.trans;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ByteHelper {
    //二个byte数组是否相等
    public static boolean isByteEqual(byte[] data1, byte[] data2) {
        if (data1 == null || data2 == null)return false;
        if (data1 == data2) return true;//地址相同
        if(data1.length != data2.length)return false;
        for (int i = 0; i < data1.length; i++) {
            if (data1[i] != data2[i])return false;
        }
        return true;
    }
    //String转为无间隔二进数组
    public static byte[] hexStringToBytesNoSpace(String hexStr) {
        if (hexStr == null || hexStr.length() == 0) return null;
        //1去除0X
        String cut0X = hexStr.replace("0X", "");
        String cut0x = cut0X.replace("0x", "");
        //2去除空格
        String cutSpace = cut0x.replace(" ", "");
        byte[] bytes    = hexStringToBytes(cutSpace);
        return bytes;
    }

    //================================================================================

    //十六进制字符串 转byte
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        //1去除0X
        String cutEND = hexString.replace("0X", "");
        cutEND = cutEND.replace("0x", "");
        //去除H -
        cutEND = cutEND.replace("H", "");
        cutEND = cutEND.replace("-", "");
        //2去除空格
        cutEND = cutEND.replace(" ", "");
        String result = cutEND.toUpperCase();
        int    length   = result.length() / 2;
        char[] hexChars = result.toCharArray();
        byte[] d        = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //byte 转 十六进制字符串
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int    v  = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
    public static String bitsToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int    v  = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
    //=======================================================
    public static String Float2Hex(float value){
        return Integer.toHexString(Float.floatToIntBits(value));
    }
    public static float Hex2Float(String hexV){
        Float value = Float.intBitsToFloat(Integer.valueOf(hexV.trim(), 16));
        return value;
    }

    /**
     * 0xFF 1byte 0x0103 2byte
     */
    public static String IntToHexStr(int value,int numBytes){
        String hv = Integer.toHexString(value);
        int needLength = numBytes*2;
        if(hv.length() == needLength)return hv;
        if(hv.length()>needLength)return hv.substring(hv.length()-needLength);
        if(hv.length()<needLength){
            String addZero = "";
            for(int i = 0; i < needLength-hv.length(); i++){
                addZero+="0";
            }
            return addZero+hv;
        }
        return hv;
    }
    public static int HexStrToInt(String byteNum){
        if(TextUtils.isEmpty(byteNum))return 0;
        return Integer.valueOf(byteNum,16);
    }
    public static double HexStrToDouble(String byteNum){
        if(TextUtils.isEmpty(byteNum))return 0;
        int value = Integer.valueOf(byteNum,16);
        return (double)value;
    }
    public static String strNumSetLen(int value,int numLength){
        String result = ""+value;
        while (result.length() < numLength){
            result = "0"+result;
        }
        return result;
    }
    //=======================================================

    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     * bit位正值
     */
    public static byte[] ByteToBitAnti(byte value) {
        byte[] byteArr = new byte[8]; //一个字节八位
        for (int i = 7; i > 0; i--) {
            byteArr[i] = (byte) (value & 1); //获取最低位
            value = (byte) (value >> 1); //每次右移一位
        }
        return byteArr;
    }
    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     * bit位反值
     */
    public static byte[] ByteToBit (byte a) {
        byte[] temp= new byte[8];
        for (int i = 7; i >= 0; i--) {
            temp[i] = (byte)((a >> i) & 1);
        }
        return temp;
    }
    public static byte BitToByte(byte[] bits) {
        byte temp = (byte) 0;
        for (int i = 0; i < bits.length; i++) {
            temp = (byte)(temp | bits[i] << i);
        }
        return temp ;
    }

    //================================================================================
    //byte 合并
    public static byte[] bytesMege(byte[] cache,byte[] mege) {
        if(cache == null && mege == null)return null;
        if(cache == null)return mege;
        if(mege == null)return cache;
        byte[] result = new byte[cache.length+mege.length];
        System.arraycopy(cache, 0, result, 0, cache.length);
        System.arraycopy(mege, 0, result, cache.length, mege.length);
        return result;
    }
    //byte 切割,返回from开始的数据
    public static byte[] bytesCut(byte[] cache,int fromPos) {
        if(cache == null)return null;
        if(cache.length<fromPos-1)return cache;
        byte[] result = new byte[cache.length-fromPos];
        System.arraycopy(cache, fromPos, result, 0, cache.length-fromPos);
        return result;
    }
    public static byte[] bytesCut(byte[] cache,int fromPos,int toPos) {
        if(cache == null)return null;
        if(cache.length<fromPos-1)return cache;
        int length = cache.length-fromPos-(cache.length-toPos);
        byte[] result = new byte[length];
        try{
            System.arraycopy(cache, fromPos, result, 0, length);
        }catch (Exception e){
            return cache;
        }
        return result;
    }
    //byte 切割,返回0：前一段，1后一段
    public static List<byte[]> bytesSplit(byte[] cache, int fromPos) {
        if(cache == null)return null;
        ArrayList<byte[]> result= new ArrayList<>();
        if(cache.length<fromPos-1){
            result.add(cache);
        }else {
            byte[] list0 = new byte[fromPos];
            byte[] list1 = new byte[cache.length - fromPos];
            System.arraycopy(cache, 0, list0, 0, fromPos);
            System.arraycopy(cache, fromPos, list1, 0, cache.length - fromPos);
            result.add(list0);
            result.add(list1);
        }
        return result;
    }

    //数据和取余
    public static String getCS_Sum_Remin(String preHexNo0x){
        if(TextUtils.isEmpty(preHexNo0x))return null;
        byte[] bytes = hexStringToBytes(preHexNo0x);
        int sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            int v  = bytes[i] & 0xFF;
            sum += v;
        }
        int dev = sum % 256;
        String result = Integer.toHexString(dev);
        if(result.length() == 1)result = "0"+result;
        return result;
    }
    //数据和取余求反
    public static String getCS_Sum_Remin_Anti(String preHexNo0x){
        if(TextUtils.isEmpty(preHexNo0x))return null;
        byte[] bytes = hexStringToBytes(preHexNo0x);
        int sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            int v  = bytes[i] & 0xFF;
            sum += v;
        }
        int dev = sum % 256;
        String result = Integer.toHexString(256-dev);
        if(result.length() == 1)result = "0"+result;
        return result;
    }
    //数据和取余求反加1,值与上面相同
    public static String getCS_Sum_Remin_Anti_Add1(String preHexNo0x){
        if(TextUtils.isEmpty(preHexNo0x))return null;
        byte[] bytes = hexStringToBytes(preHexNo0x);
        int sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            int v  = bytes[i] & 0xFF;
            sum += v;
        }
        int dev = sum % 256;
        String value = Integer.toHexString(dev);
        if(value.length() == 1)value = "0"+value;
        //求和取余完成
        byte[] bytes1 = hexStringToBytes(value);
        byte bt = (byte) ~bytes1[0];
        //求反完成
        int v  = bt & 0xFF;
        if(bt==0xFF)return "01";
        v +=1;
        //加1完成
        String result = Integer.toHexString(v);
        if(result.length() == 1)result = "0"+result;
        if(result.length() > 2)result = result.substring(result.length()-2);
//        Log.e("Remin_Anti_Add1","result:"+v+" CS:"+result);
        return result.toUpperCase();
    }
    //==========================取二byte Double值================
    /**取二byte Double值 byte值反向*/
    public static double getTwoByteValueAnti(byte[] data,int posFrom,int posTo){
        byte[] byte2 = ByteHelper.bytesCut(data,posFrom,posTo);
        byte[] antiByte = new byte[]{byte2[1],byte2[0]};
        String str3 = ByteHelper.bytesToHexString(antiByte);
        long result = Long.valueOf(str3,16).shortValue();
        return 1.0*result;
    }
    /**取二byte Double值*/
    public static double getTwoByteValue(byte[] data,int posFrom,int posTo){
        byte[] byte2 = ByteHelper.bytesCut(data,posFrom,posTo);
        String str3 = ByteHelper.bytesToHexString(byte2);
        long result = Long.valueOf(str3,16).shortValue();
        return 1.0*result;
    }
    public static double getOneByteValue(byte data){
        byte[] byte2 = new byte[]{data};
        String str3 = ByteHelper.bytesToHexString(byte2);
        long result = Long.valueOf(str3,16).shortValue();
        return 1.0*result;
    }
}
