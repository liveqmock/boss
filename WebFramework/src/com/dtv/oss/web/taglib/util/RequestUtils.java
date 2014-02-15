package com.dtv.oss.web.taglib.util;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.dtv.oss.web.util.WebPrint;

public class RequestUtils {
  /**
   * Maps lowercase JSP scope names to their PageContext integer constant values.
   */
  private static Map scopes = new HashMap();

  /**
   * Set up the scope map values.
   */
  static {
      scopes.put("page", new Integer(PageContext.PAGE_SCOPE));
      scopes.put("request", new Integer(PageContext.REQUEST_SCOPE));
      scopes.put("session", new Integer(PageContext.SESSION_SCOPE));
      scopes.put("application", new Integer(PageContext.APPLICATION_SCOPE));
  }

  /**
     * Locate and return the specified bean, from an optionally specified
     * scope, in the specified page context.  If no such bean is found,
     * return <code>null</code> instead.  If an exception is thrown, it will
     * have already been saved via a call to <code>saveException()</code>.
     *
     * @param pageContext Page context to be searched
     * @param name Name of the bean to be retrieved
     * @param scopeName Scope to be searched (page, request, session, application)
     *  or <code>null</code> to use <code>findAttribute()</code> instead
     * @return JavaBean in the specified page context
     * @exception JspException if an invalid scope name
     *  is requested
     */
    public static Object lookup(PageContext pageContext, String name, String scopeName)
        throws JspException {

        if (scopeName == null) {
                return pageContext.findAttribute(name);
        }

        try {
            return pageContext.getAttribute(name, getScope(scopeName));

        } catch (JspException e) {
            WebPrint.PrintErrorInfo("RequestUtils", "lookup method -- "+e.getMessage());
        }

        return null;
    }

    /**
     * Converts the scope name into its corresponding PageContext constant value.
     * @param scopeName Can be "page", "request", "session", or "application" in any
     * case.
     * @return The constant representing the scope (ie. PageContext.REQUEST_SCOPE).
     * @throws JspException if the scopeName is not a valid name.
     * @since Struts 1.1
     */
    public static int getScope(String scopeName) throws JspException {
                Integer scope = (Integer) scopes.get(scopeName.toLowerCase());

                if (scope == null) {
                        WebPrint.PrintErrorInfo("RequestUtils", "getScope method -- scopeName is not a valid.");
                        return PageContext.APPLICATION_SCOPE;
                }

                return scope.intValue();
    }


}