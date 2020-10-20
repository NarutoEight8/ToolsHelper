package com.naruto.toolshelper;


import com.naruto.toolshelper.datatypetrans.ByteHelper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String MD5generator(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    public static boolean MD5Equal(String inputStr,String genMD5){
        String strBuild = MD5generator(inputStr);
        strBuild = strBuild.toUpperCase();
        String genMD5Up = genMD5.toUpperCase();
        if(strBuild.equals(genMD5Up))return true;
        return false;
    }
    //============================================================
    /**00FF**/
    public static String CRC16_MODBUS(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        byte[] bt = intToBytes(CRC);
        return ByteHelper.bytesToHexString(bt);
    }
    private static byte[] intToBytes(int value) {
        byte[] src = new byte[2];
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }
    /**FF00**/
    public static String CRC16_MODBUS_anti(byte[] bytes) {
        String result = CRC16_MODBUS(bytes);
        return result.substring(2,4)+result.substring(0,2);
    }

}
