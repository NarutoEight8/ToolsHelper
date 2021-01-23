package com.nathan96169.toolshelper.encryption;

import com.nathan96169.toolshelper.trans.ByteHelper;

public class CRC {
    /**
     * 计算CRC16校验码
     */
    public static String getCRC16(byte[] bytes,boolean isAnti) {
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
        String result = ByteHelper.IntToHexStr(CRC,2);
        if(isAnti)result = result.substring(2) + result.substring(0,2);
        return result.toUpperCase();
    }
}
