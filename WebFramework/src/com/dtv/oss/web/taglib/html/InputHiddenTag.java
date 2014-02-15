package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.jsp.JspException;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.util.*;


/**
 * Custom tag that represents an HTML hidden element of input
 *
 */

public class InputHiddenTag extends BaseHandlerTag {
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
   public void setValue(String pValue) {
     if(pValue!=null)
      {
          value = CommonUtils.GetBeanPropertyReturnString(pageContext, pValue);
          if(value==null)
          {
              value = pageContext.getRequest().getParameter(pValue);
          }

          if(this.value==null)
          {
              this.value = "";
          }

     }

   }


   protected String name = null;

   public String getName() {
       return (this.name);
   }

   public void setName(String name) {
       this.name = name;
   }

   public int doStartTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

        // Create an appropriate "form" element based on our parameters
        StringBuffer results = new StringBuffer("<input");
        results.append(" type=\"hidden\" name=\"");
        results.append(name);
        results.append("\"");

        results.append(" value=\"");
        results.append(value);
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


        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(">");

        // Print this field to our output writer
        ResponseUtils.write(pageContext, results.toString());

        return (SKIP_BODY);

    }






    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();

        value = null;
        name = null;

    }

}