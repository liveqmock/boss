package com.dtv.oss.web.taglib.logic;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import com.dtv.oss.web.util.WebPrint;


public abstract class BaseCompareTag extends BodyTagSupport {

    protected boolean testRes = false;

    /**
     * If testRes= true , skip is invalid. If skip = true and testRes = true, the left page which are behind the iftag will not be skipped.
     */
    protected boolean skip = false;

    public String getSkip(){
        return String.valueOf(skip);
    }
    public void setSkip(String pSkip){
        pSkip = pSkip.trim();

        if (pSkip.compareToIgnoreCase("TRUE")==0) skip = true;
        else skip = false;
    }

    /**
     * The name of the JSP bean to be used as a variable (if
     * <code>property</code> is not specified), or whose property is to be
     * accessed (if <code>property</code> is specified).
     */
    protected String name = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The name of the HTTP request parameter to be used as a variable.
     */
    protected String parameter = null;

    public String getParameter() {
        return (this.parameter);
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    /**
     * The name of the bean property to be used as a variable.
     */
    protected String property = null;

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }



    /**
     * The scope to search for the bean named by the name property, or
     * "any scope" if null.
     */
    protected String scope = null;

    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Evaluate the condition that is being tested by this particular tag,
     * and return <code>true</code> if the nested body content of this tag
     * should be evaluated, or <code>false</code> if it should be skipped.
     * This method must be implemented by concrete subclasses.
     *
     * @exception JspException if a JSP exception occurs
     */
    protected abstract boolean condition() throws JspException;


        public int doStartTag() throws JspException
        {

            testRes = condition();
            if (testRes) return (EVAL_BODY_BUFFERED);
              else return (SKIP_BODY);
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

        public int doEndTag() throws JspException {

                //if testRes = false, skip will be invalid.
                if (!testRes) return (EVAL_PAGE);

                if (skip) return (SKIP_PAGE);
                else return (EVAL_PAGE);

        }

    /**
     * Release all allocated resources.
     */
    public void release() {

        super.release();
        name = null;
        parameter = null;
        property = null;
        scope = null;
        skip = false;
        testRes = false;

    }


}