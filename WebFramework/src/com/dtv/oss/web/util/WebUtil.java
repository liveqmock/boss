package com.dtv.oss.web.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.lang.reflect.*;
import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.util.AbstractCollection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;

public class WebUtil {

	public static boolean StringHaveContent(String strVal) {
		if (!(strVal == null || "".equals(strVal.trim())))
			return true;
		else
			return false;
	}

	public static boolean StringHaveIndexContent(String[] strVal, int length) {
		if (!(strVal == null || "".equals(strVal)))
			return true;
		if (strVal.length != length)
			return true;
		else
			return false;
	}

	/**
	 * identify int
	 */
	public static boolean IsInt(String strVal) {

		if (StringHaveContent(strVal)) {
			try {
				int iVal = Integer.valueOf(strVal).intValue();
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			} catch (Exception e) {//normal process for unknown exception
				return false;
			}
		}

		return false;

	}

	/**
	 * string to int
	 */
	public static int StringToInt(String strVal) {
		int iVal = 0;

		if (StringHaveContent(strVal)) {
			try {
				iVal = Integer.valueOf(strVal).intValue();
			} catch (NumberFormatException nfe) {
				WebPrint.PrintErrorInfo("WebUtil",
						"Method StringToInt Number Format Exception" + strVal);
			} catch (Exception e) {//normal process for unknown exception
				WebPrint.PrintErrorInfo("WebUtil", "Method StringToInt "
						+ e.getMessage());
			}
		}

		return iVal;

	}

	/**
	 * string to Integer
	 */
	public static Integer StringToInteger(String strVal) {
		int iVal = StringToInt(strVal);

		return new Integer(iVal);

	}

	public static long StringToLong(String strVal) {
		long lVal = 0;

		if (StringHaveContent(strVal)) {
			try {
				lVal = Long.valueOf(strVal).longValue();
			} catch (NumberFormatException nfe) {
				WebPrint.PrintErrorInfo("WebUtil",
						"Method StringToLong Number Format Exception" + strVal);
			} catch (Exception e) {//normal process for unknown exception
				WebPrint.PrintErrorInfo("WebUtil", "Method StringToInt "
						+ e.getMessage());
			}
		}

		return lVal;

	}

	public static double StringTodouble(String strVal) {
		double fVal = 0;

		if (StringHaveContent(strVal)) {
			try {
				fVal = Double.valueOf(strVal).doubleValue();
			} catch (NumberFormatException nfe) {
				WebPrint.PrintErrorInfo("WebUtil",
						"Method StringTodouble Number Format Exception"
								+ strVal);
			} catch (Exception e) {//normal process for unknown exception
				WebPrint.PrintErrorInfo("WebUtil", "Method StringToInt "
						+ e.getMessage());
			}
		}

		return fVal;

	}

	public static java.sql.Date StringToDate(String strVal) {
		java.sql.Date dt = null;

		if (StringHaveContent(strVal)) {
			try {
				dt = java.sql.Date.valueOf(strVal);
			} catch (Exception e) {//normal process for unknown exception
				WebPrint.PrintErrorInfo("WebUtil", "Method StringToDate "
						+ e.getMessage());
			}
		}

		return dt;

	}

	public static boolean isAM(java.sql.Timestamp tVal) {
		if (tVal == null)
			return false;

		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(tVal.getTime());

		if (cal.get(Calendar.AM_PM) == Calendar.AM)
			return true;
		else
			return false;
	}

	public static boolean isPM(java.sql.Timestamp tVal) {
		if (tVal == null)
			return false;

		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(tVal.getTime());

		if (cal.get(Calendar.AM_PM) == Calendar.PM)
			return true;
		else
			return false;
	}

	public static boolean IsDate(String strVal) {

		if (!StringHaveContent(strVal))
			return false;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;

		try {
			date = format.parse(strVal, new ParsePosition(0));
		} catch (Exception e) {
		}

		if (date != null)
			return true;
		else
			return false;

	}

	public static java.sql.Timestamp StringToTimestamp(String strVal) {
		java.sql.Timestamp dt = null;

		if (StringHaveContent(strVal)) {
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
				WebPrint.PrintErrorInfo("WebUtil", "Method StringToDate "
						+ e.getMessage());
			}
		}

		return dt;

	}

	public static java.sql.Timestamp StringToTimestampWithDayBegin(String strVal) {
		//this line add by zhouxushun
		if (strVal == null || "".equals(strVal))
			return null;

		java.sql.Timestamp dt = StringToTimestamp(strVal);

		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(dt);

		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);

		return new java.sql.Timestamp(gc.getTime().getTime());

	}

	public static java.sql.Timestamp StringToTimestampDefaultDayBegin(
			String strVal, String strHour) {
		if (StringHaveContent(strHour))
			strVal = strVal + " " + strHour + ":00:00";

		java.sql.Timestamp dt = StringToTimestamp(strVal);

		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(dt);

		if (!StringHaveContent(strHour)) {
			gc.set(Calendar.HOUR_OF_DAY, 0);
			gc.set(Calendar.MINUTE, 0);
			gc.set(Calendar.SECOND, 0);
			gc.set(Calendar.MILLISECOND, 0);
		}

		return new java.sql.Timestamp(gc.getTime().getTime());

	}

	public static java.sql.Timestamp StringToTimestampWithDayEnd(String strVal) {
		//this line add by zhouxushun
		if (strVal == null || "".equals(strVal))
			return null;

		java.sql.Timestamp dt = StringToTimestamp(strVal);

		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(dt);

		gc.set(Calendar.HOUR_OF_DAY, 23);
		gc.set(Calendar.MINUTE, 59);
		gc.set(Calendar.SECOND, 59);
		gc.set(Calendar.MILLISECOND, 999);

		return new java.sql.Timestamp(gc.getTime().getTime());

	}

	public static java.sql.Timestamp StringToTimestampDefaultDayEnd(
			String strVal, String strHour) {
		if (StringHaveContent(strHour))
			strVal = strVal + " " + strHour + ":00:00";

		java.sql.Timestamp dt = StringToTimestamp(strVal);

		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(dt);

		if (!StringHaveContent(strHour)) {
			gc.set(Calendar.HOUR_OF_DAY, 23);
			gc.set(Calendar.MINUTE, 59);
			gc.set(Calendar.SECOND, 59);
			gc.set(Calendar.MILLISECOND, 999);
		}

		return new java.sql.Timestamp(gc.getTime().getTime());

	}

	/**
	 * Format is defined by pattern.
	 */
	public static String TimestampToString(java.sql.Timestamp tVal,
			String pattern) {
		String sDate = "";
		if (tVal == null)
			return sDate;

		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			sDate = df.format(tVal);
		} catch (Exception e) {//normal process for unknown exception
			WebPrint.PrintErrorInfo("WebUtil",
					"Method TimestampToString with Pattern " + e.getMessage());
		}

		return sDate;

	}

	public static int getHourOfTimestamp(java.sql.Timestamp tVal) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(tVal);

		return gc.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinuteOfTimestamp(java.sql.Timestamp tVal) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(tVal);

		return gc.get(Calendar.MINUTE);
	}

	public static int getSecondOfTimestamp(java.sql.Timestamp tVal) {
		java.util.Calendar gc = GregorianCalendar.getInstance();
		gc.clear();
		gc.setTime(tVal);

		return gc.get(Calendar.SECOND);
	}

	public static boolean StringToBoolean(String strVal) {

		if (StringHaveContent(strVal)) {
			if (strVal.equalsIgnoreCase("true"))
				return true;
		}

		return false;

	}

	/**
	 * int to string:
	 * if digit>0 and the length of string is less than digit, zero will be added before the stirng
	 * until the length of string is equivalent with digit
	 */
	public static String IntToString(int iVal, int digit) {
		String sPattern = "";
		for (int i = 0; i < digit; i++)
			sPattern = sPattern + "0";
		DecimalFormat df = new DecimalFormat(sPattern);

		return df.format(iVal);

	}

	/**
	 * double to string:
	 * if digit>0 and the length of string is less than digit, zero will be added before the stirng
	 * until the length of string is equivalent with digit
	 */
	public static String doubleToString(double fVal, int fixed) {
		String sPattern = "";

		if (fixed > 0)
			sPattern = ".";
		for (int i = 0; i < fixed; i++)
			sPattern = sPattern + "0";
		DecimalFormat df = new DecimalFormat(sPattern);

		return df.format(fVal);

	}

	/**
	 *Remove 'return' charactors, \r or \n, from string
	 */
	public static String RemoveAwayReturn(String str) {
		if (str == null)
			return str;

		String strRemove = str;

		strRemove = strRemove.replaceAll("\r", "");
		strRemove = strRemove.replaceAll("\n", "");

		return strRemove;
	}

	/**
	 * returns name which is added with "get" at the beginning of the string
	 * and the first character is capital.
	 * For example, name is "from" and then returns "getFrom"
	 */
	public static String Name2GetMethod(String name) {
		if (name == null)
			return null;

		String rtn = "get" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		return rtn;
	}

	/**
	 * excutes a method through its name.
	 */
	public static Object excuteMethod(Object pClass, String name) {
		Method meth = null;
		Object result = null;

		if ((pClass == null) || (name == null))
			return null;

		try {
			meth = pClass.getClass().getMethod(name, null);
		} catch (NoSuchMethodException e) {
			WebPrint.PrintErrorInfo("WebUtil", "excuteMethod -- method " + name
					+ " does not exist.");
		} catch (Exception e) {
			WebPrint.PrintErrorInfo("WebUtil",
					"excuteMethod -- get method exception: " + e.getMessage());
		}
		if (meth != null) {
			try {
				result = meth.invoke(pClass, null);
			} catch (Exception e) {
				WebPrint.PrintErrorInfo("WebUtil",
						"excuteMethod -- get invoke method getlist exception: "
								+ e.getMessage());
			}
		}

		return result;
	}

	/**
	 * excutes a method through its name.
	 */
	public static String excuteMethodReturnString(Object pClass, String name) {

		Object result = excuteMethod(pClass, name);

		if (result == null)
			return null;
		else
			return result.toString();
	}

	public static void main(String[] args) {
		//            String str="2004-2-2";
		//        	java.sql.Timestamp dt = StringToTimestamp(str);
		//        	
		//        	System.out.println(dt.toString());

		double d = 49.14;
		double fval = new Double(d).doubleValue();
		String str = doubleToString(fval, 4);

		System.out.println(str);
	}

	public static synchronized ArrayList parseSerialNo(String sCmd) {
		if (sCmd == null || sCmd.length() == 0)
			return new ArrayList();
		String sPM[] = sCmd.split("/");

		ArrayList list1 = new ArrayList();
		for (int i = 0; i < sPM.length; i++) {

			if (sPM[i].trim().length() == 0)

				continue;

			list1.add(sPM[i].trim());

		}

		return list1;
	}

	/**
	 * 该方法主要用于menu显示的时候用，这个不适用于其他用途
	 * @param countSize
	 * @param currentIndex
	 * @return
	 */
	public static String compositeMenuParam(int countSize, int currentIndex) {
		String compositeParam = "";
		String showOrHid = "";
		for (int i = 1; i <= countSize; i++) {
			if (i == currentIndex)
				showOrHid = "show";
			else
				showOrHid = "hide";
			if ("".equals(compositeParam))
				compositeParam = "'Layer" + i + "','','" + showOrHid + "'";
			else
				compositeParam += ",'Layer" + i + "','','" + showOrHid + "'";
		}
		return compositeParam;
	}

	/**
	 *  页面显示null问题。
	 *  参数如果是对象空(null)，则转为字符串空("")。
	 */
	public static String NullToString(String name) {
		if (name == null)
			return "";
		else
			return name;
	}

	public static boolean isChangeValue(Timestamp source, Timestamp target) {
		boolean isChanged = false;
		if (target != null && !"".equals(target)) {
			if (source == null || "".equals(source))
				isChanged = true;
			else if (source != null && !"".equals(source)
					&& !target.equals(source))
				isChanged = true;
		} else {
			if (source != null && !"".equals(source))
				isChanged = true;
		}
		return isChanged;
	}

	public static boolean isChangeValue(String source, String target) {
		boolean isChanged = false;
		if (target != null && !"".equals(target)) {
			if (source == null || "".equals(source))
				isChanged = true;
			else if (source != null && !"".equals(source)
					&& !target.equals(source))
				isChanged = true;
		} else {
			if (source != null && !"".equals(source))
				isChanged = true;
		}
		return isChanged;
	}
  	public static String getStringByAbstractCollection(AbstractCollection list){
  		if(list==null)return null;
  		String res=list.toString();
  		res=res.substring(0, res.length()-1);
  		res=res.substring(1, res.length());
  		return res;
  	}
	public static ArrayList getIntegerListByString(String s) {
		if (s == null || "".equals(s)) {
			return null;
		}
		s = s.replaceAll("[^\\d]++", ",");
		while (s.startsWith(",") || s.endsWith(",")) {
			if (s.startsWith(",")) {
				s = s.substring(1, s.length());
			}
			if (s.endsWith(",")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		ArrayList res = new ArrayList();
		String[] arrS = s.split(",");
		for (int i = 0; i < arrS.length; i++) {
			res.add(new Integer(arrS[i]));
		}
		return res;
	}

	public static boolean isChangeValue(int source, int target) {
		boolean isChanged = false;
		if (source != target)
			isChanged = true;
		return isChanged;
	}

	//解决“科学记数表示法“的问题，屏蔽掉科学记数表示法
	public static BigDecimal bigDecimalFormat(double double1) {
		BigDecimal big = new BigDecimal(double1);
		//doubleValue()
		return big.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	//格式化保留两位小数
	public static double doubleFormat(double double1) {
		java.text.DecimalFormat format = (java.text.DecimalFormat) java.text.DecimalFormat
				.getInstance();
		//把浮点数格式化为两位小数的double
		format.applyPattern(".00");
		return Double.parseDouble(format.format(double1));
	}

	public static double StringToDouble(String strVal) {
	        double fVal=0;

	        if (StringHaveContent(strVal))
	        {
	            try
	            {
	                fVal=Double.valueOf(strVal).doubleValue();
	            }
	            catch(NumberFormatException nfe) {
	                WebPrint.PrintErrorInfo("WebUtil", "Method StringToDouble Number Format Exception"+strVal);
	            }
	            catch(Exception e) {//normal process for unknown exception
	                WebPrint.PrintErrorInfo("WebUtil", "Method StringToDouble "+e.getMessage());
	            }
	        }

	        return fVal;

	}
	
	public static String WriteSpecialChar(String sourceString){
  	  return sourceString.replaceAll("\"","&#34");
    }
}