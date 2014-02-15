package com.dtv.oss.web.taglib.util;


import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import java.util.regex.PatternSyntaxException;

import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.WebPrint;

/**
*Include some functions used by classes of tag library
*/

public class CommonUtils {



	public static Object GetBeanProperty(PageContext pageContext, String text)
	    {
	    	String[] aProperty = null;
	    	try
	    	{
	    		aProperty = text.split(":");
	    	}
	    	catch(PatternSyntaxException e)
	    	{
			System.out.println("!Error: CommonUtils.GetBeanProperty--split error, PatternSyntaxException "+e.getMessage());
			return null;
	    	}
	    	catch(NullPointerException e)
	    	{
			System.out.println("!Error: CommonUtils.GetBeanProperty--parameter text is null");
			return null;
	    	}

	    	Object obj = pageContext.findAttribute(aProperty[0]);
	    	for(int i=1;i<aProperty.length;i++)
	    	{
	    		//String property = aProperty[i];
                        obj = WebUtil.excuteMethod(obj, WebUtil.Name2GetMethod(aProperty[i]));
                        if (obj==null) return null;


	    	}
	    	return obj;


	}


        public static String GetBeanPropertyReturnString(PageContext pageContext, String text)
        {
        	Object result = GetBeanProperty(pageContext, text);
        	try
        	{	
        		if (result==null) 
        			return null;
        		else if(result instanceof Double){
        			//解决“科学记数表示法“的问题
        			BigDecimal big = new BigDecimal(((Double)result).doubleValue());
        			result = big.setScale(2,BigDecimal.ROUND_HALF_EVEN).toString();
        		}
        		else 
        			return result.toString();
        	}
        	catch (Exception e)
        	{
               WebPrint.PrintErrorInfo("CommonUtils", e.getMessage());
        	}
        	return result.toString();
        }
}







