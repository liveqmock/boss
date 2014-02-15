package com.dtv.oss.web.taglib.pri;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.filter.ProtectedResource;

public class IsAuthorized extends BodyTagSupport{
    private String name = null;

    public String getName(){
        return name;
    }

    public void setName(String pName){
        name = pName;
    }

    public int doStartTag() throws JspException
    {
        if (name == null) {
            WebPrint.PrintErrorInfo(this.getClass().getName(), "the name is not be set!");
            return (SKIP_BODY);
        }

        if (PrivilegeUtil.isAuthorized(pageContext.getSession(), name))
          return EVAL_BODY_BUFFERED;
        else
          return (SKIP_BODY);


    }


      public int doAfterBody() throws JspException {

          WebPrint.PrintTagDebugInfo(this.getClass().getName(),"doAfterBody enter ...");

          if (bodyContent != null)
          {
              try
              {
                  JspWriter out = pageContext.getOut();
                  if (out instanceof BodyContent)
                    out = ((BodyContent) out).getEnclosingWriter();
                  out.println(bodyContent.getString());
              }
              catch(Exception e)
              {
                  WebPrint.PrintErrorInfo(this.getClass().getName(),e.getMessage());
              }
              bodyContent.clearBody();
          }

          WebPrint.PrintTagDebugInfo(this.getClass().getName(),"doAfterBody return.");

          return (SKIP_BODY);

      }

    public void release() {

        super.release();
        name = null;
    }

}