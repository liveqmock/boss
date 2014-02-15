package com.dtv.oss.web.taglib.template;

// J2EE Imports
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtv.oss.web.view.Parameter;
import com.dtv.oss.web.view.Screen;

// WAF imports
import com.dtv.oss.web.util.WebKeys;

/**
 * This class is an easy interface to the JSP template or other
 * text that needs to be inserted.
 */

public class InsertTag extends TagSupport {

    private boolean directInclude = false;
    private String parameter = null;
    private Parameter parameterRef = null;
    private Screen screen = null;

    /**
     * default constructor
     */
    public InsertTag() {
        super();
    }

    public void setParameter(String parameter){
        this.parameter = parameter;
    }

    public int doStartTag() throws JspTagException {
         try{
             pageContext.getOut().flush();
         } catch (Exception e){
             // do nothing
         }
        // load the ScreenFlow
        try {
                screen = (Screen)pageContext.getRequest().getAttribute(WebKeys.CURRENT_SCREEN);
        } catch (NullPointerException e){
            throw new JspTagException("Error extracting Screen from session: " + e);
        }
        if ((screen != null) && (parameter != null)) {
            parameterRef = (Parameter)screen.getParameter(parameter);
        } else {
            System.err.println("InsertTag: screenManager is null");
        }
        if (parameterRef != null) directInclude = parameterRef.isDirect();
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        try {
            if (directInclude && parameterRef != null) {
            	if(parameterRef.getValue()==null ||"".equals(parameterRef.getValue()))
            		pageContext.getOut().print("»¶Ó­ÄúÊ¹ÓÃ"+pageContext.getSession().getAttribute(WebKeys.CMS_MSOSYSTEMNAME));
            	else
            		pageContext.getOut().print(parameterRef.getValue());
            } else if (parameterRef != null)  {
                if (parameterRef.getValue() != null) pageContext.getRequest().getRequestDispatcher(parameterRef.getValue()).include(pageContext.getRequest(), pageContext.getResponse());
            }
         } catch (Exception ex) {
             System.err.println("InsertTag:doEndTag caught: " + ex);
             ex.printStackTrace();
        }
        return EVAL_PAGE;
    }
}