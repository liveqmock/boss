package com.dtv.oss.web.taglib.html;



import javax.servlet.jsp.JspException;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.util.ResponseUtils;

public class WriteSpecialCharTag extends WriteTag {
    public void PrintInHtml() throws JspException{
        if (value!=null)
          ResponseUtils.write(pageContext, value.toString().replaceAll("\"","&#34"));
        else
          WebPrint.PrintDebugInfo("WriteTag", "property "+property+" does not exist or equals null !");

    }

}
