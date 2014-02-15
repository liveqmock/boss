package com.dtv.oss.web.taglib.util;


import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import com.dtv.oss.web.util.WebPrint;

/**
 * General purpose utility methods related to generating a servlet response
 * in the Struts controller framework.
 *
 */

public class ResponseUtils {


    /**
     * Write the specified text as the response to the writer associated with
     * this page.  <strong>WARNING</strong> - If you are writing body content
     * from the <code>doAfterBody()</code> method of a custom tag class that
     * implements <code>BodyTag</code>, you should be calling
     * <code>writePrevious()</code> instead.
     *
     * @param pageContext The PageContext object for this page
     * @param text The text to be written
     *
     * @exception JspException if an input/output error occurs (already saved)
     */
    public static void write(PageContext pageContext, String text)
        throws JspException {

        JspWriter writer = pageContext.getOut();
        try {
            if (text!=null) writer.print(text);
        } catch (IOException e) {
            WebPrint.PrintErrorInfo("ResponseUtils", "write out exception "+e.getMessage());
        } catch(Exception e) {//normal process for unknown exception
            WebPrint.PrintErrorInfo("ResponseUtils", "unknown exception "+e.getMessage());
        }
    }



}
