package com.dtv.oss.web.taglib.html;


import javax.servlet.jsp.JspException;

import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.util.ResponseUtils;

public class WriteNumberTag extends WriteTag {
    protected int digit = 0;

    public String getDigit() {
        return String.valueOf(digit);
    }

    public void setDigit(String pDigit) {
        if (WebUtil.StringHaveContent(pDigit))
          digit = WebUtil.StringToInt(pDigit);
    }

    /**
     *hidezero means that it will not be displayed if the value is null or 0
     */
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

    public void PrintInHtml() throws JspException{
        if (value==null) value = Integer.valueOf("0");

        if (value instanceof Integer)
        {
            Integer intVal = (Integer)value;

            //hidezero = true and value=0 ,it will not be printed
            if ((hidezero)&&(intVal.intValue()==0)) return;

            ResponseUtils.write(pageContext, WebUtil.IntToString(intVal.intValue(), digit));
        }
        else
            WebPrint.PrintErrorInfo(this.getClass().getName(),"value --"+value.toString()+"-- is not Integer.");

    }


    public void release() {

        super.release();
        digit = 0;
        hidezero = false;

    }

}