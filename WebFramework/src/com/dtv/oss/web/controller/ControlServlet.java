package com.dtv.oss.web.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

// J2EE Imports

// WAF imports
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.flow.ScreenFlowManager;

/**
 * <p>Title: BOSS 2</p>
 * <p>Description: BOSS second iterative development project</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai DigiVision Technology Co. Ltd</p>
 * <p>This servlet processes requests of *.do</p>
 * @author Leon Liu
 * @version 1.2
 */



public class ControlServlet extends HttpServlet {
    //static final private String CONTENT_TYPE = "text/html; charset=GBK";
    private ServletContext context;
    private HashMap urlMappings;
    private HashMap eventMappings;
    //private Locale defaultLocale = null;
    //private RequestProcessor requestProcessor;

    public void init(ServletConfig config) throws ServletException {
        System.out.println("init ControlServlet Class");
        super.init(config);

        //String defaultLocaleString = config.getInitParameter("default_locale");
        //defaultLocale = I18nUtil.getLocaleFromString(defaultLocaleString);
        this.context = config.getServletContext();

        //未完成的部分！！！
        //setDebugEnviornment();

        String requestMappingsURL = null;
        try {
            requestMappingsURL = context.getResource("/WEB-INF/mappings.xml").toString();
        } catch (java.net.MalformedURLException ex) {
            System.err.println("MainServlet: initializing ScreenFlowManager malformed URL exception: " + ex);
        }

       urlMappings = URLMappingsXmlDAO.loadRequestMappings(requestMappingsURL);
       context.setAttribute(WebKeys.URL_MAPPINGS, urlMappings);
       eventMappings = URLMappingsXmlDAO.loadEventMappings(requestMappingsURL);
       context.setAttribute(WebKeys.EVENT_MAPPINGS, eventMappings);
       getScreenFlowManager();
       getRequestProcessor();
    }

    public  void doGet(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
         doProcess(request, response);
    }

    public  void doPost(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
    	String sidebarMenuId  =request.getParameter("tableId");
    	String sidebarTdId =request.getParameter("sel_bar");
    	if (sidebarMenuId ==null){
    		sidebarMenuId =(String)request.getSession().getAttribute(WebKeys.SIDERBARMENUID);
    	}
    	if (sidebarTdId ==null){
    		sidebarTdId =(String)request.getSession().getAttribute(WebKeys.SIDERBARTDID);
    	}   	
    	request.getSession().setAttribute(WebKeys.SIDERBARMENUID,sidebarMenuId);
    	request.getSession().setAttribute(WebKeys.SIDERBARTDID,sidebarTdId);
    	doProcess(request, response);

    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response)
                   throws IOException, ServletException {
        // set the locale of the user to default if not set
        /*if (request.getSession().getAttribute(WebKeys.LOCALE) == null) {
            request.getSession().setAttribute(WebKeys.LOCALE, defaultLocale);
        }*/

        try {
                 getRequestProcessor().processRequest(request);
                 getScreenFlowManager().forwardToNextScreen(request,response);
        } catch (Throwable ex) {
            String className = ex.getClass().getName();
            String nextScreen = getScreenFlowManager().getExceptionScreen(ex);
            // put the exception in the request
            request.setAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE, ex);
            if (nextScreen == null) {
                // send to general error screen
                ex.printStackTrace();
                throw new ServletException("ControlServlet: unknown exception: " + className);
            }
            context.getRequestDispatcher(nextScreen).forward(request, response);
        }

    }

    private void setDebugEnviornment() {
        String debugFlag=context.getInitParameter("boss.debug.flag");
        if (debugFlag==null) return;
        int iDebug = 0;
        try
        {
            iDebug=Integer.valueOf(debugFlag).intValue();
        }
        catch(NumberFormatException nfe) {
            System.out.println("boss.debug.flag Number Format Exception :"+debugFlag);
        }
        catch(Exception e) {//normal process for unknown exception
            System.out.println("convert boss.debug.flag exception : "+e.getMessage());
        }

        if ((iDebug & 1)==0) WebKeys.DEBUG_ENVIRONMENT_FLAG = false;
          else WebKeys.DEBUG_ENVIRONMENT_FLAG = true;

        if ((iDebug & 2)==0) WebKeys.DEBUG_PRIVILEGE_FLAG = false;
          else WebKeys.DEBUG_PRIVILEGE_FLAG = true;

        if ((iDebug & 4)==0) WebKeys.WEBACTION_GET_PARAMETER_FLAG = false;
          else WebKeys.WEBACTION_GET_PARAMETER_FLAG = true;

        if ((iDebug & 8)==0) WebKeys.DEBUG_TAG_DEBUG_FLAG = false;
          else WebKeys.DEBUG_TAG_DEBUG_FLAG = true;
    }

    private RequestProcessor getRequestProcessor() {
         RequestProcessor rp = (RequestProcessor)context.getAttribute(WebKeys.REQUEST_PROCESSOR);
         if ( rp == null ) {
             rp = new RequestProcessor();
             rp.init(context);
             context.setAttribute(WebKeys.REQUEST_PROCESSOR, rp);
        }
       return rp;
    }

    private ScreenFlowManager getScreenFlowManager() {
            ScreenFlowManager screenManager = (ScreenFlowManager)context.getAttribute(WebKeys.SCREEN_FLOW_MANAGER);
            if (screenManager == null ) {
                screenManager = new ScreenFlowManager();
                screenManager.init(context);
                context.setAttribute(WebKeys.SCREEN_FLOW_MANAGER, screenManager);
             }
        return screenManager;
    }

    /**
     * The UrlMapping object contains information that will match
     * a url to a mapping object that contains information about
     * the current screen, the HTMLAction that is needed to
     * process a request, and the HTMLAction that is needed
     * to insure that the propper screen is displayed.
    */

    private URLMapping getURLMapping(String urlPattern) {
        if ((urlMappings != null) && urlMappings.containsKey(urlPattern)) {
            return (URLMapping)urlMappings.get(urlPattern);
        } else {
            return null;
        }
    }

}


