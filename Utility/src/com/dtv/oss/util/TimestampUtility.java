package com.dtv.oss.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

public class TimestampUtility {

	public static java.sql.Timestamp getCurrentDate() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}

	public static java.sql.Timestamp getCurrentDateWithoutTime() {
		return ClearTimePart(getCurrentDate());
	}

	public static java.sql.Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/*
	 * because Timestamp(int year, int month, int date, int hour, int minute,
	 * int second, int nano) is Deprecated. So write this method to finish this
	 * function
	 */
	public static Timestamp getCertainTimestamp(int year, int month, int date) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month - 1);
		gc.set(Calendar.DATE, date);

		return new java.sql.Timestamp(gc.getTime().getTime());
	}

	/*
	 * because Timestamp(int year, int month, int date, int hour, int minute,
	 * int second, int nano) is Deprecated. So write this method to finish this
	 * function
	 */
	public static Timestamp getCertainTimestamp(int year, int month, int date,
			int hour, int minute, int second) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month - 1);
		gc.set(Calendar.DATE, date);

		gc.set(Calendar.HOUR_OF_DAY, hour);
		gc.set(Calendar.MINUTE, minute);
		gc.set(Calendar.SECOND, second);
		gc.set(Calendar.MILLISECOND, 0);

		return new java.sql.Timestamp(gc.getTime().getTime());
	}

	/*
	 * return YYYY-MM-DD
	 */
	public static String TimestampToDateString(Timestamp tVal) {
		if (tVal == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(tVal);
	}

	/*
	 * when tVal = 2004-11-26 16:10:51.743 invoke ClearTimePart(t); return
	 * 2004-11-26
	 */
	public static Timestamp ClearTimePart(Timestamp tVal) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(tVal);

		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);

		return new java.sql.Timestamp(gc.getTime().getTime());
	}

	/*
	 * when tVal = 2004-11-26 16:10:51.743 invoke TimestampMove(t, 0, -3, -2);
	 * return 2004-08-24 16:10:51.743
	 */
	public static Timestamp TimestampMove(Timestamp tVal, int year, int month,
			int day) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(tVal);

//		if (year != 0)
//			gc.set(Calendar.YEAR, gc.get(Calendar.YEAR) + year);
//		if (month != 0)
//			gc.set(Calendar.MONTH, gc.get(Calendar.MONTH) + month);
//		if (day != 0)
//			gc.set(Calendar.DATE, gc.get(Calendar.DATE) + day);
		if (year != 0)
			gc.add(Calendar.YEAR, year);
		if (month != 0)
			gc.add(Calendar.MONTH, month);
		if (day != 0)
			gc.add(Calendar.DATE, day);
		return new java.sql.Timestamp(gc.getTime().getTime());
	}

	/*
	 * when tVal = 2004-11-26 invoke getTimeEnd(t, 12); return 2005-11-25
	 * 23:59:59.999
	 */
	public static java.sql.Timestamp getTimeEnd(java.sql.Timestamp timestart,
			int monthlength) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(timestart);
		if (monthlength != 0) {
			gc.add(Calendar.MONTH, monthlength);
//			gc.set(Calendar.MONTH, gc.get(Calendar.MONTH) + monthlength);
			gc.set(Calendar.DATE, gc.get(Calendar.DATE) - 1);
			gc.set(Calendar.HOUR_OF_DAY, 23);
			gc.set(Calendar.MINUTE, 59);
			gc.set(Calendar.SECOND, 59);
			gc.set(Calendar.MILLISECOND, 999);
		}

		return new java.sql.Timestamp(gc.getTime().getTime());
	}

	/**
	 * @param timestart
	 *            开始时间
	 * @param type
	 *            (Y:lengeth:表示以年为单位；M：表示以月为单位；D：表示以天为单位)
	 * @param length
	 *            长度
	 * @return
	 */
	public static java.sql.Timestamp getTimeEnd(java.sql.Timestamp timestart,
			String type, int length) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(timestart);
		//旧的写法,有问题,2008-8-31,加6个月,会变成2009-3-2,这是错误的,应该使用add方法设置平移时长
//		if ("Y".equals(type)) {
//			gc.set(Calendar.MONTH, gc.get(Calendar.MONTH) + length * 12);
//		} else if ("M".equals(type)) {
//			gc.set(Calendar.MONTH, gc.get(Calendar.MONTH) + length);
//		} else if ("D".equals(type)) {
//			gc.set(Calendar.MONTH, gc.get(Calendar.MONTH));
//			gc.set(Calendar.DATE, gc.get(Calendar.DATE) + length);
//		}
		if ("Y".equals(type)) {
			gc.add(Calendar.YEAR, length );
		} else if ("M".equals(type)) {
			gc.add(Calendar.MONTH, length);
		} else if ("D".equals(type)) {
			gc.add(Calendar.DATE, length );
		}
		if (length > 0)
			gc.set(Calendar.DATE, gc.get(Calendar.DATE) - 1);
		gc.set(Calendar.HOUR_OF_DAY, 23);
		gc.set(Calendar.MINUTE, 59);
		gc.set(Calendar.SECOND, 59);
		gc.set(Calendar.MILLISECOND, 999);

		return new java.sql.Timestamp(gc.getTime().getTime());
	}

	/**
	 * 得到该月份的第一天邮戳
	 * 
	 * @return
	 */
	public static java.sql.Timestamp getMonthFirstDay(
			java.sql.Timestamp timestart) {
		java.util.Calendar gc = GregorianCalendar.getInstance();

		gc.clear();
		gc.setTime(timestart);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		// gc.set(Calendar.DATE, gc.get(Calendar.DATE) + 1);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);

		return new java.sql.Timestamp(gc.getTime().getTime());
	}

	public static int getDaysBetweenTwoDay(java.sql.Timestamp timestart,
			java.sql.Timestamp timeend) {
		int days = (int) ((timestart.getTime() - timeend.getTime())
				/ (24 * 60 * 60 * 1000) + 1);
		return days;

	}

	/*
	 * when tVal = 2004-11-26 invoke getTimeEndWithoutTime(t, 12); return
	 * 2005-11-25 23:59:59.999
	 */
	public static java.sql.Timestamp getTimeEndWithoutTime(
			java.sql.Timestamp timestart, int monthlength) {
		return ClearTimePart(getTimeEnd(timestart, monthlength));
	}

	/**
	 * @param timestart
	 *            开始时间
	 * @param type
	 *            (Y:lengeth:表示以年为单位；M：表示以月为单位；D：表示以天为单位)
	 * @param length
	 *            长度
	 * @return
	 */
	public static java.sql.Timestamp getTimeEndWithoutTime(
			java.sql.Timestamp timestart, String type, int length) {
		return ClearTimePart(getTimeEnd(timestart, type, length));
	}
	public static java.sql.Timestamp getDateEnd(java.sql.Timestamp timestart,
			String type, int length) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(timestart);
		if (length > 0) {
			if ("Y".equals(type)) {
				gc.add(Calendar.YEAR, length );
			} else if ("M".equals(type)) {
				gc.add(Calendar.MONTH, length);
			} else if ("D".equals(type)) {
				gc.add(Calendar.DATE, length );
			}
		}
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		return new java.sql.Timestamp(gc.getTime().getTime());
	}
	public static void main(String[] args) {
		System.out.println(TimestampUtility.getCurrentDate());
		System.out.println(TimestampUtility.getCurrentDate());
		System.out.println(TimestampUtility.getTimeEnd(getCurrentTimestamp(),
				"D", 1));
		System.out.println("getCertainTimestamp:::"+getCertainTimestamp(2008,8,31));

		System.out.println("getTimeEnd:::"+TimestampUtility.getTimeEnd(getCertainTimestamp(2008,8,30),"M",6));
		System.out.println("getTimeEnd:M::"+TimestampUtility.getTimeEnd(getCertainTimestamp(2008,8,31),6));
		System.out.println("getDateEnd:M::"+TimestampUtility.getDateEnd(getCertainTimestamp(2008,8,31),"M",6));
		System.out.println("getDateEnd:Y::"+TimestampUtility.getDateEnd(getCertainTimestamp(2008,8,31),"Y",6));
		System.out.println("getDateEnd:D::"+TimestampUtility.getDateEnd(getCertainTimestamp(2009,2,27),"D",6));

		System.out.println(TimestampUtility
				.getMonthFirstDay(getCurrentTimestamp()));
	}

	public static java.sql.Timestamp StringToTimestamp(String strVal) {
		java.sql.Timestamp dt = null;

		if (strVal != null && !strVal.equals("")) {
			if (strVal.indexOf("-") < 0) {
				strVal = "2004-10-01 " + strVal;
			}

			if (strVal.indexOf(":") < 0) {
				//缺少时间部分，补上
				strVal += " 00:00:00";
			}

			try {
				dt = java.sql.Timestamp.valueOf(strVal);
			} catch (Exception e) {//normal process for unknown exception
				LogUtility.log(TimestampUtility.class, LogLevel.DEBUG, e
						.getMessage());
			}
		}

		return dt;
	}
	
	public static Timestamp getSystemDate() {
		return getCurrentDateWithoutTime();
	}

	public static String getSystemDate(int countDay) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(getTimeEnd(TimestampUtility
				.getCurrentDateWithoutTime(), "D", countDay));
	}

	public static String getSystemDate(Timestamp sourceTime, int countDay) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df
				.format(getTimeEnd(sourceTime, "D", countDay));
	}
	
	/**
	 * 日期格式化
	*/
	public static String getDate(Date strDate, String pattern)throws Exception {
		SimpleDateFormat  dateFormat=new SimpleDateFormat(pattern);  
		String test = dateFormat.format(strDate).toString();
		return test;
	}
}