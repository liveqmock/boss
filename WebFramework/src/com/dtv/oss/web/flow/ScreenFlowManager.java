package com.dtv.oss.web.flow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Iterator;

// waf imports
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.controller.URLMappingsXmlDAO;
import com.dtv.oss.web.controller.URLMapping;
import com.dtv.oss.web.flow.ScreenFlowData;
import com.dtv.oss.web.flow.FlowHandler;
import com.dtv.oss.web.exception.FlowHandlerException;


/**
 * This file looks at the Request URL and maps the request
 * to the page for the web-templating mechanism.
 */
public class ScreenFlowManager implements java.io.Serializable {

    private HashMap screens;
    private HashMap urlMappings;
    private HashMap exceptionMappings;
    private HashMap screenDefinitionMappings;
    private String defaultScreen = "";
    private ServletContext context;

    public ScreenFlowManager() {
        screens = new HashMap();
    }

    public void init(ServletContext context) {
        this.context = context;
        String requestMappingsURL = null;
        try {
            requestMappingsURL = context.getResource("/WEB-INF/mappings.xml").toString();
        } catch (java.net.MalformedURLException ex) {
            System.err.println("ScreenFlowManager: initializing ScreenFlowManager malformed URL exception: " + ex);
        }
        urlMappings = (HashMap)context.getAttribute(WebKeys.URL_MAPPINGS);
        ScreenFlowData screenFlowData = URLMappingsXmlDAO.loadScreenFlowData(requestMappingsURL);
        defaultScreen = screenFlowData.getDefaultScreen();
        exceptionMappings = screenFlowData.getExceptionMappings();
    }

    /**
     * The UrlMapping object contains information that will match
     * a url to a mapping object that contains information about
     * the current screen, the WebAction that is needed to
     * process a request, and the WebAction that is needed
     * to insure that the propper screen is displayed.
    */

    private URLMapping getURLMapping(String urlPattern) {
        if ((urlMappings != null) && urlMappings.containsKey(urlPattern)) {
            return (URLMapping)urlMappings.get(urlPattern);
        } else {
            return null;
        }
    }

    public String getExceptionScreen(String exceptionClassName) {
        return (String)exceptionMappings.get(exceptionClassName);
    }


    /**
     * Using the information we have in the request along with
     * The url map for the current url we will insure that the
     * propper current screen is selected based on the settings
     * in both the screendefinitions.xml file and requestmappings.xml
     * files.
    */
    public void forwardToNextScreen(HttpServletRequest request, HttpServletResponse response)
              throws java.io.IOException, FlowHandlerException, javax.servlet.ServletException {
        // set the presious screen
        String fullURL = request.getRequestURI();
        // get the screen name
        String selectedURL = defaultScreen;
        int lastPathSeparator = fullURL.lastIndexOf("/") + 1;
        if (lastPathSeparator != -1) {
            selectedURL = fullURL.substring(lastPathSeparator, fullURL.length());
        }
        String currentScreen = "";
        URLMapping urlMapping = getURLMapping(selectedURL);
        if (urlMapping != null) {
            if (!urlMapping.useFlowHandler()) {
                currentScreen = urlMapping.getScreen();
            } else {
                // load the flow handler
                FlowHandler handler = null;
                String flowHandlerString = urlMapping.getFlowHandler();
                try {
                    handler = (FlowHandler)getClass().getClassLoader().loadClass(flowHandlerString).newInstance();
                    // invoke the processFlow(HttpServletRequest)
                    handler.doStart(request);
                    String flowResult = handler.processFlow(request);
                    handler.doEnd(request);
                    currentScreen = urlMapping.getResultScreen(flowResult);
                    // if there were no screens by the id then assume that the result was
                    //the screen itself
                    if (currentScreen == null) currentScreen = flowResult;
               } catch (Exception ex) {
                   System.err.println("ScreenFlowManager caught loading handler: " + ex);
               }
            }
        }
        if (currentScreen == null) {
            System.err.println("ScreenFlowManager: Screen not found for " + selectedURL);
            throw new RuntimeException("Screen not found for " + selectedURL);
        }
        context.getRequestDispatcher("/" + currentScreen).forward(request, response);

    }
    /**
            go through the list and use the Class.isAssignableFrom(Class method)
            to see it is a subclass of one of the exceptions
    */
    public String getExceptionScreen(Throwable e) {
        Iterator it = exceptionMappings.keySet().iterator();
        while (it.hasNext()) {
            String exceptionName = (String)it.next();
            Class targetExceptionClass = null;
            try {
                targetExceptionClass = this.getClass().getClassLoader().loadClass(exceptionName);
            } catch (ClassNotFoundException cnfe) {
                System.err.println("ScreenFlowManager: Could not load exception " + exceptionName);
            }
            System.err.println("Checking exception: " + exceptionName + " against " + e.getClass().getName());
            // check if the exception is a sub class of matches the exception
            if ((targetExceptionClass != null) &&
                targetExceptionClass.isAssignableFrom(e.getClass())) {
                System.err.println("ScreenFlowManager: found exception for " + exceptionName + " matches");;
                return "/" + (String)exceptionMappings.get(exceptionName);
            }
        }
        return null;
    }

    public void setDefaultScreen(String defaultScreen) {
        this.defaultScreen = defaultScreen;
    }

    /**
     * Returs the current screen
     */

    public String getCurrentScreen(HttpSession session)  {
        return (String)session.getAttribute(WebKeys.CURRENT_SCREEN);
    }
}


