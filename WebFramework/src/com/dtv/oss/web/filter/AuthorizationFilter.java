package com.dtv.oss.web.filter;

import java.io.IOException;
import java.util.*;
import java.net.URL;

// J2EE imports
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Cookie;

import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.PrivilegeUtil;

public class AuthorizationFilter implements Filter {

    // these static strings define where to put/get things
    /*public static final String FORM_SIGNON_URL = "j_signon_check";
    public static final String FORM_USER_NAME = "j_username";
    public static final String FORM_PASSWORD = "j_password";
    public static final String REMEMBER_USERNAME = "j_remember_username";
    public static final String USER_NAME = "j_signon_username";
    public static final String SIGNED_ON_USER  = "j_signon";
    public static final String ORIGINAL_URL = "j_signon_original_url";
    public static final String CREATE_USER_URL = "j_create_user";
    public static final String COOKIE_NAME = "bp_signon";
    */

    public static final int ERROR_PRIVILEGE_SESSION_NULL = -1;
    public static final int ERROR_PRIVILEGE_NOT_AUTHORIZED = -2;

    private HashMap protectedResources;
    private FilterConfig config = null;
    private String signOnErrorPage = null;
    private String signOnPage = null;
    private String userCreationError = null;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        URL protectedResourcesURL = null;

        String bossFlag = config.getServletContext().getInitParameter("boss.type.flag");
        if (bossFlag==null)
        {
            bossFlag = "";
            System.out.println("boss.type.flag is null");
        }

        String resourceURL = "/WEB-INF/signon-config";
        if (!bossFlag.trim().equals("")) resourceURL += "-" + bossFlag;
        resourceURL += ".xml";

        try {
            protectedResourcesURL = config.getServletContext().getResource(resourceURL);
            SignOnDAO dao = new SignOnDAO(protectedResourcesURL);
            signOnErrorPage = dao.getSignOnErrorPage();
            signOnPage = dao.getSignOnPage();
            protectedResources = dao.getProtectedResources();

        } catch (java.net.MalformedURLException ex) {
            WebPrint.PrintErrorInfo(this.getClass().getName(), "init -- malformed URL exception:"+ex.getMessage());
        }

        config.getServletContext().setAttribute(WebKeys.FILTER_PRIVILEGE, protectedResources);
    }

    public void destroy() {
        config = null;
    }

     public  void doFilter(ServletRequest request, ServletResponse  response, FilterChain chain)
        throws IOException, ServletException {

         HttpServletRequest hreq = (HttpServletRequest)request;
         HttpServletResponse hres = (HttpServletResponse)response;
         String currentURI = hreq.getRequestURL().toString();
         String currentURL = hreq.getRequestURI();

         //WebPrint.PrintPrivilegeDebugInfo("invoke doFilter URL=" + currentURL);

         // get everything after the context root
         int lastSlash = currentURL.lastIndexOf("/");
         String targetURL = null;
         if (lastSlash >= 0) targetURL = currentURL.substring(lastSlash + 1, currentURL.length());
           else targetURL = currentURL;

         //WebPrint.PrintPrivilegeDebugInfo("targetURL="+targetURL);
         if (targetURL != null) {

            if (targetURL.indexOf(".jsp")>=0)
            {
                if (!validateJspAccess(targetURL))
                {
                    WebPrint.PrintErrorInfo(this.getClass().getName(), targetURL + " is not allowed to access.");
                    hres.sendRedirect(signOnErrorPage);
                }
            }
            else
            {
                if (PrivilegeUtil.isProtectedResource(hreq, targetURL))
                {
                     if (PrivilegeUtil.isAuthorized(hreq, targetURL))
                         WebPrint.PrintPrivilegeDebugInfo(targetURL+" is authorized.");
                     else
                     {
                         WebPrint.PrintPrivilegeDebugInfo(targetURL+" is not authorized.");
                         hres.sendRedirect(signOnErrorPage);
                         return;
                     }
                }
                else WebPrint.PrintPrivilegeDebugInfo(targetURL+" is not protected.");
            }

         }

         chain.doFilter(request,response);
    }

/*
     public  int CompareOperatorPrivilege(HttpServletRequest request, ProtectedResource resource)
        throws IOException, ServletException {

        Map mapPrivilege = (Map)request.getSession().getAttribute(WebKeys.PRIVILEGE_SESSION_NAME);

        if (mapPrivilege==null) return ERROR_PRIVILEGE_SESSION_NULL;

        ArrayList listRole = resource.getRoles();

        //Role is not defined, need not check concrete privileges
        if ((listRole==null)||(listRole.size()<=0)) return 0;

        for (int i=0; i<listRole.size(); i++)
        {
            Object strRole = listRole.get(i);
            Object strPriv = mapPrivilege.get(strRole);
            if (strPriv!=null) return 0;

        }
        return ERROR_PRIVILEGE_NOT_AUTHORIZED;
     }
*/
     public  boolean validateJspAccess(String strURL)
     {
         //now jsp is not be validated with module number.
         //in the future, it will be corrected.
//         if (strURL.compareToIgnoreCase("template.jsp")==0) return true;
         if (strURL.toLowerCase().endsWith("template.jsp".toLowerCase())) return true;

         return false;

     }

}

