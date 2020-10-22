package com.nathan96169.toolshelper.trans;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class ODateTime {
	public static String long2Str(long time,String format){
		if(time<=0)return "0";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(time);
		String re_StrTime = sdf.format(date);
		return re_StrTime;
	}
	public static String time2YearOnly(long time) {
		return long2Str(time,"yyyy");
	}
	public static String time2FromYear2Day(long time) {
		return long2Str(time,"yyyy-MM-dd");
	}
	public static String time2FromYear2DayCN(long time) {
		return long2Str(time,"yyyy年MM月dd日");
	}
	public static String time2yyyy年MM月dd日_EEE(long time) {
		return long2Str(time,"yyyy年MM月dd日 EEE");
	}
	public static String time2FromYear2Min(long time) {
		return long2Str(time,"yyyy-MM-dd HH:mm");
	}
	public static String time2FromYear2MinCN(long time) {
		return long2Str(time,"yyyy年MM月dd日 HH:mm");
	}
	public static String time2SecNumber(long time) {
		return long2Str(time,"yyyyMMddHHmmss");
	}
	public static String time2FromYear2Sec(long time) {
		return long2Str(time,"yyyy-MM-dd HH:mm:ss");
	}
	public static String time2FromMonth2MinCN(long time) {
		return long2Str(time,"MM月dd日 HH:mm");
	}
	public static String time2FromHour2Min(long time) {
		return long2Str(time,"HH:mm");
	}
	public static String time2FromHour2Sec(long time) {
		return long2Str(time,"HH:mm:ss");
	}
	/**yyyy_MM_dd_HH_mm*/
	public static String time2StringName(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_HH_mm_ss");
		String re_StrTime = sdf.format(new Date(time));
		return re_StrTime;
	}
	public static String time2StringLine2(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日\n HH:mm:ss");
		String re_StrTime = sdf.format(new Date(time));
		return re_StrTime;
	}
	/** timestamp */
	public static long date2long(int yyyy, int MM, int dd) {
		try {
			String timeStr = yyyy + "年" + MM + "月" + dd + "日";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			Date date = sdf.parse(timeStr);
			long l = date.getTime();
			return l;
		} catch (ParseException e) {
			Log.e("Exception",e.toString());
		}
		return 0;
	}
	/** timestamp */
	public static long time2long(int yyyy, int MM, int dd, int HH, int mm, int ss) {
		try {
			String timeStr = yyyy + "年" + MM + "月" + dd + "日" + HH + "时" + mm + "分" + ss + "秒";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
			Date date = sdf.parse(timeStr);
			long l = date.getTime();
			return l;
		} catch (ParseException e) {
			Log.e("Exception",e.toString());
		}
		return 0;
	}
	/** timestamp "yyyy-MM-dd" yyyy*/
	public static long str2long(String yyyyMMdd,String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(yyyyMMdd);
			long l = date.getTime();
			return l;
		} catch (ParseException e) {
			Log.e("Exception",e.toString());
		}
		return 0;
	}
	/** timestamp */
	public static long getNow() {
		Date date = new Date();
		long l = date.getTime();
		return l;
	}
	/** timestamp */
	public static long get0ClockFromDay(long timeStamp) {
		Calendar cal= Calendar.getInstance();
		cal.setTimeInMillis(timeStamp);
		cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
		return cal.getTimeInMillis();
	}
	/** timestamp */
	public static long get24ClockFromDay(long timeStamp) {
		Calendar cal= Calendar.getInstance();
		cal.setTimeInMillis(timeStamp);
		cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59,59);
		return cal.getTimeInMillis();
	}

	// 返回时间格式如：2020-02-17 00:00:00
	public static long getStartOfDay(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime().getTime();
	}
	// 返回时间格式如：2020-02-19 23:59:59
	public static long getEndOfDay(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime().getTime();
	}
}
