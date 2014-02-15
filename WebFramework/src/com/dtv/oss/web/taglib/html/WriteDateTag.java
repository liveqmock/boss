package com.dtv.oss.web.taglib.html;


import java.util.*;

import javax.servlet.jsp.JspException;

import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.util.ResponseUtils;

public class WriteDateTag extends WriteTag {
   public final static String DATETIME_OF_TODAY = "NOW";
   public final static String DATETIME_OF_TICKMORNING = "TICKMORNING";
   public final static String DATETIME_OF_TICKAFTERNOON = "TICKAFTERNOON";
   
   public final static int SPECIAL_OPERATION_OF_TICKMORNING = 1;
   public final static int SPECIAL_OPERATION_OF_TICKAFTERNOON = 2;
   
   //sepcialRequestCode will be given zero without special request.
   protected int sepcialRequestCode = 0;
   
  //如果pattern被设置，includeTime和isChinese将无效
  protected String pattern = null;

  public String getPattern() {
      return (this.pattern);
  }

  public void setPattern(String val) {
      this.pattern = val;
  }

    //hidezero is only effective when value is instance  of Long
    protected boolean hidezero = false;

    public String getHideZero() {
        return String.valueOf(hidezero);
    }

    public void setHideZero(String pVal) {
        if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
          hidezero=true;
        else
          hidezero=false;
    }

    //includeTime means exporting time with the date
    protected boolean includeTime = false;

    public String getIncludeTime() {
        return String.valueOf(includeTime);
    }

    public void setIncludeTime(String pVal) {
        if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
          includeTime=true;
        else
          includeTime=false;
    }

    //  only time part displayed
    protected boolean onlyTime = false;

    public String getOnlyTime() {
        return String.valueOf(onlyTime);
    }

    public void setOnlyTime(String pVal) {
        if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
          onlyTime=true;
        else
          onlyTime=false;
    }
    
    //hidezero means that it will not be displayed if the value is null or 0
    protected boolean isChinese = false;

    public String getIsChinese() {
        return String.valueOf(isChinese);
    }

    public void setIsChinese(String pVal) {
        if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
          isChinese=true;
        else
          isChinese=false;
    }

    public void setCertainDateFlag(String pVal) {
        Calendar cal = Calendar.getInstance();

        if (pVal!=null)
        {
            if (pVal.equalsIgnoreCase(DATETIME_OF_TODAY))
              value = new java.sql.Timestamp(cal.getTime().getTime());
            else if (pVal.equalsIgnoreCase(DATETIME_OF_TICKMORNING))
            	sepcialRequestCode = SPECIAL_OPERATION_OF_TICKMORNING;
            else if (pVal.equalsIgnoreCase(DATETIME_OF_TICKAFTERNOON))
            	sepcialRequestCode = SPECIAL_OPERATION_OF_TICKAFTERNOON;
        }

    }

    public void PrintInHtml() throws JspException{
        if (value==null) return;
        //if (value==null) value = Long.valueOf("0");

        if (value instanceof java.sql.Timestamp)
        {
            java.sql.Timestamp tVal = (java.sql.Timestamp)value;
            
            if (sepcialRequestCode != 0)
            {
            	//special conditions
            	switch (sepcialRequestCode)
				{
            	    case SPECIAL_OPERATION_OF_TICKMORNING:
            	    	if (WebUtil.isAM(tVal))
            	    		ResponseUtils.write(pageContext, new String("\u221A".getBytes()));
            	    	return;
            	    case SPECIAL_OPERATION_OF_TICKAFTERNOON:
            	    	if (WebUtil.isPM(tVal))
            	    		ResponseUtils.write(pageContext, new String("\u221A".getBytes()));
            	    	return;
				}
            }           	
            
            if (!WebUtil.StringHaveContent(pattern))
            {
            	if (!onlyTime)
            	{
            		if (isChinese) pattern = "yyyy年MM月dd日";
            		else pattern = "yyyy-MM-dd";

            		if (includeTime)
            		{
            			if (isChinese) pattern += " HH时mm分ss秒";
            			else pattern += " HH:mm:ss";
            		}
            	}
            	else
            	{
            		if (isChinese) pattern = "HH时mm分ss秒";
        			else pattern = "HH:mm:ss";
            	}
            }

            ResponseUtils.write(pageContext, WebUtil.TimestampToString(tVal, pattern));

        }
        /*
        if (value instanceof Long)
        {
            Long longVal = (Long)value;
            if (!WebUtil.StringHaveContent(pattern))
            {
              if (isChinese) pattern = "yyyy年MM月dd日";
                else pattern = "yyyy-MM-dd";

              if (includeTime)
              {
                if (isChinese) pattern += " HH时mm分ss秒";
                  else pattern += " HH:mm:ss";
              }

            }

            //hidezero = true and value=0 ,it will not be printed
            if (hidezero&&(longVal.longValue()==0)) return;

            ResponseUtils.write(pageContext, WebUtil.LongToDate(longVal.longValue(), pattern));
        }*/
        else
            WebPrint.PrintErrorInfo(this.getClass().getName(),"value --"+value.toString()+"-- is not Long, so it cannot be converted with the format of date.");

    }


    public void release() {

        super.release();
        sepcialRequestCode = 0;
        pattern=null;
        hidezero = false;
        includeTime = false;
        onlyTime=false;
        isChinese=false;
    }

}
