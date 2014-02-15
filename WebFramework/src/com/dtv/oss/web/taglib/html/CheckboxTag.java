package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.*;
import javax.servlet.jsp.tagext.*;
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.taglib.util.*;

public class CheckboxTag extends BaseHandlerTag {


    // ----------------------------------------------------- Instance Variables

    protected String name = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    protected String value = null;

    /**
     * Return the comparison value.
     */
    public String getValue() {
        return (this.value);
    }

    /**
     * Set the comparison value.
     *
     * @param value The new comparison value
     */
    public void setValue(String value) {
      if(value!=null)
      {
          this.value = CommonUtils.GetBeanPropertyReturnString(pageContext,value);
          if(this.value==null)
          {
              this.value = pageContext.getRequest().getParameter(value);
          }

          if(this.value==null)
          {
              this.value = value;
          }

     }

    }

    protected String match = null;

    public String getMatch() {
        return (this.match);
    }

    public void setMatch(String match) {
        if(match!=null)
        {
            this.match = CommonUtils.GetBeanPropertyReturnString(pageContext,match);
            if(this.match==null)
            {
                this.match = pageContext.getRequest().getParameter(match);
            }

            if(this.match==null)
            {
                this.match = match;
            }

       }
    }

    /**
     * Should multiple selections be allowed.  Any non-null value will
     * trigger rendering this.
     */
    protected boolean multipleMatchFlag = false;

    public String getMultipleMatchFlag() {
        return String.valueOf(multipleMatchFlag);
    }

    public void setMultipleMatchFlag(String multipleMatchFlag) {
        this.multipleMatchFlag = WebUtil.StringToBoolean(multipleMatchFlag);
    }




    // --------------------------------------------------------- Public Methods


    /**
     * <p>
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

        // Create an appropriate "form" element based on our parameters
        StringBuffer results = new StringBuffer("<input type=\"checkbox\" ");
        results.append(" name=\"");
        results.append(name);
        results.append("\"");
        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }
        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }

        if (value!=null) {
            results.append(" value=\"");
            results.append(value);
            results.append("\"");
        }

        if (WebUtil.StringHaveContent((String)match)&&(value!=null)) {
            String sRelVal = value;
            if (multipleMatchFlag)
            {
              match=";"+match+";";
              sRelVal=";"+value+";";
            }
            if (match.indexOf(sRelVal)>=0)
            results.append(" checked ");
        }

        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(">");

        // Print this field to our output writer
        ResponseUtils.write(pageContext, results.toString());

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

        return (SKIP_BODY);

    }


    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        name = null;
        value = null;

        match = null;
        multipleMatchFlag = false;

    }

}