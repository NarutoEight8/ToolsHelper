package com.naruto.toolshelper.trans;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**数据类型转换**/
public class ONumTrans {

	//=====================String型=======================
	/**patterns: "   0" */
	public static String int2RightStr(int value, int totalSpace) {
		String resultString = ""+value;
		for(int i = resultString.length();i<totalSpace;i++){
			resultString = " "+resultString;
		}
		return resultString;
	}
	public static String int2RightStr(int value, int totalSpace,String emptyStr) {
		String resultString = ""+value;
		for(int i = resultString.length();i<totalSpace;i++){
			resultString = emptyStr+resultString;
		}
		return resultString;
	}
	/**patterns: "   0" */
	public static String float2RightStr(float value, int digit) {
		String resultString = ""+value;
		String[] arr = resultString.split("\\.");
		int realDigit = arr.length<2 ? 0 : arr[1].length();
		if(realDigit == 0 && digit>0)resultString+=".";
		for(int i = realDigit;i<digit;i++){
			resultString += "0";
		}
		return resultString;
	}

	/**
	 * double值位数显示
	 **/
	public static double doubleDisplay(double value, int digits) {
		String patterns = "#0";//0
		if (digits == 1) patterns = "#0.0";
		if (digits == 2) patterns = "#0.00";
		if (digits == 3) patterns = "#0.000";
		DecimalFormat decimalFormat = new DecimalFormat(patterns);
		String resultString = decimalFormat.format(value);
		return Double.valueOf(resultString);
	}
	public static String doubleToStr(double value, int digits) {
		String patterns = "#0";//0
		if (digits == 1) patterns = "#0.0";
		if (digits == 2) patterns = "#0.00";
		if (digits == 3) patterns = "#0.000";
		DecimalFormat decimalFormat = new DecimalFormat(patterns);
		String resultString = decimalFormat.format(value);
		return resultString;
	}
	/**patterns: #000 */
	public static String num2Str(Number value, String patterns) {
		DecimalFormat decimalFormat = new DecimalFormat(patterns);
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		String resultString = decimalFormat.format(value);
		return resultString;
	}
	/**patterns: "   0" */
	public static String double2RightStr(double value, int totalSpace,int digits) {
		String resultString = doubleToStr(value,digits);
		for(int i = resultString.length();i<totalSpace;i++){
			resultString = " "+resultString;
		}
		return resultString;
	}
	//=====================int型=======================
	public static int str2int(String str) {
		try {
			if (str == null || str.equals("")) return -1;
			return Integer.valueOf(str.trim());
		}catch (NumberFormatException e){
			return -1;
		}
	}
	//=====================bytes型=======================
	private static byte[] intToBytes(int value) {
		byte[] src = new byte[2];
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) (value & 0xFF);
		return src;
	}

	//=====================double型=======================
	public static double str2double(String str) {
		if (str == null || str.equals("")) return 0;
		try {
			return Double.valueOf(str.trim());
		}catch (Exception e){
			return 0;
		}
	}
}
