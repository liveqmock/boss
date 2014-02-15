package com.dtv.oss.web.taglib.html;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.util.ResponseUtils;

public class WriteSpecialCharParameterTag extends BodyTagSupport {

  /**
   * the Name of the paramter of the request
   */
    private String name = null;

    public String getName(){
        return name;
    }
    public void setName(String pName){
        name = pName;
    }

    public int doStartTag() throws JspException {

        // checks the name is valid.
        if (name==null)
        {
            WebPrint.PrintErrorInfo(this.getClass().getName(),"the name of the parameter is not set!");
            return (EVAL_BODY_BUFFERED);
        }

        try
        {
            if (pageContext.getRequest().getParameter(name) != null)
                ResponseUtils.write(pageContext, pageContext.getRequest().getParameter(name).replaceAll("\"","&#34"));
        }
        catch(Exception e)
        {
            WebPrint.PrintErrorInfo(this.getClass().getName()," doStartTag"+e.getMessage());
        }


        return (EVAL_BODY_BUFFERED);
    }


    public void release() {

        super.release();
        name = null;

    }

}
