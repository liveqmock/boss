/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.util;
import java.util.*;

import com.dtv.oss.service.util.BusinessUtility;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HelperCommonUtil {

	/**
	 * 
	 */
	public HelperCommonUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static boolean StringHaveContent(String strVal) {
		if ((strVal != null)&&(strVal.trim().equals("")==false)) return true;
			else return false;
	}
  
	public static boolean EqualString(String str1, String str2) {
	  	if (!StringHaveContent(str1))
	  	{
	  		if (StringHaveContent(str2)) return false;
	  		else return true;
	  	}
  	
	  	if (!StringHaveContent(str2)) return false;
	  	
	  	return str1.equals(str2);
	}
  
	public static double adddouble(double f1, double f2) {
	  	double d1 = f1;
	  	double d2 = f2;
	  	
	  	return (new Double(d1+d2)).doubleValue();
	}
  
	public static double addDouble2double(double f1, double d2) {
	  	double d1 = f1;
	  	
	  	return (new Double(d1+d2)).doubleValue();
	}
	/**
	 * 通过设备类型取得设备类型的名称
	 * @param deviceClass
	 * @return
	 */
	public static String getNameByDeviceClass(String deviceClass){
		String currentClassName="";
		Map currentMap=BusinessUtility.getDeiceClassMop();
		currentClassName=(String)currentMap.get(deviceClass);
		return currentClassName;
	}
	/**
	 * 取得公用数据中定的key对应的名称
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static String getCommonNameByCacheNameAndKey(String cacheName,String key){
		String commonName="";
		commonName=BusinessUtility.getCommonNameByKey(cacheName,key);
		return commonName;
	}

	//add by zhouxushun , String类型转换成int类型
	public static int String2Int(String str){
		if(str==null)
			return 0;
		return Integer.parseInt(str);
	}
	/**
	 * 把带逗号分割的字符串转换为集合
	 * @param sourceStr
	 * @return
	 */
	public  static Collection splitStringToCol(String sourceStr) {
		Collection splitedCol=new ArrayList();
		String[] sourceArray=sourceStr.split(",");
		for(int i=0;i<sourceArray.length;i++){
			splitedCol.add(sourceArray[i]);
		}
		return splitedCol;
	}
	
	public static java.sql.Timestamp StringToTimestamp(String strVal) {
        java.sql.Timestamp dt=null;

        if (StringHaveContent(strVal))
        {
        	if (strVal.indexOf("-")<0)
            {
        		strVal = "2004-10-01 "+ strVal;
            }
        	
            if (strVal.indexOf(":")<0)
            {
              //缺少时间部分，补上
              strVal += " 00:00:00";
            }

            try
            {
                dt=java.sql.Timestamp.valueOf(strVal);
            }
            catch(Exception e) {//normal process for unknown exception
                System.out.println(e.getMessage());
            }
        }

        return dt;

    }
	
	public static void main(String[] args) {
	}
}
