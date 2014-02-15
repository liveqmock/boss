package com.dtv.oss.web.controller;

import java.util.HashMap;

// J2ee Imports
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.rmi.RemoteException;
//WAF imports
import com.dtv.oss.web.util.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.web.action.WebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.controller.exception.EJBEventException;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.web.controller.ComponentManager;
import com.dtv.oss.web.util.CurrentOperator;

/**
 * This is the web tier controller helper class.
 *
 * This class is responsible for processing all requests received from
 * the Main.jsp and generating necessary events to modify data which
 * are sent to the WebController.
 *
 */
public class RequestProcessor implements java.io.Serializable {

    private HashMap urlMappings;
    private HashMap eventMappings;

    public RequestProcessor() {}


    public void init(ServletContext context) {
        urlMappings = (HashMap)context.getAttribute(WebKeys.URL_MAPPINGS);
        eventMappings = (HashMap)context.getAttribute(WebKeys.EVENT_MAPPINGS);
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

    /**
     * The EventMapping object contains information that will match
     * a event class name to an EJBActionClass.
     *
    */

    private EventMapping getEventMapping(EJBEvent eventClass) {
        // get the fully qualified name of the event class
        String eventClassName = eventClass.getClass().getName();
        System.out.println("EJBEvent name is:" + eventClassName);
        if ((eventMappings != null) && eventMappings.containsKey(eventClassName)) {
            return (EventMapping)eventMappings.get(eventClassName);
        } else {
            return null;
        }
    }

    /**
    * This method is the core of the RequestProcessor. It receives all requests
    *  and generates the necessary events.
    */
    public void processRequest(HttpServletRequest request) throws WebActionException,
        EJBEventException, ServletException,CommandException, RemoteException {

        EJBEvent ev = null;
        String fullURL = request.getRequestURI();
        // get the screen name
        String selectedURL = null;
        int lastPathSeparator = fullURL.lastIndexOf("/") + 1;
        if (lastPathSeparator != -1) {
            selectedURL = fullURL.substring(lastPathSeparator, fullURL.length());
        }
        URLMapping urlMapping = getURLMapping(selectedURL);
        WebAction action = getAction(urlMapping);
        if (action != null) {
            ServletContext sc = request.getSession().getServletContext();
            action.setServletContext(sc);
            action.doStart(request);
            ev = action.perform(request);
            CommandResponse eventResponse = null;
            if (ev != null) {
               // set the ejb action class name on the event
                EventMapping eventMapping = getEventMapping(ev);
                if (eventMapping != null) {
                    System.out.println(eventMapping.getEJBCommandClassName());
                    ev.setEJBCommandClassName(eventMapping.getEJBCommandClassName());

                    //set current operator id, added by lhd 2003-10-13
                    //for using universally, acquire Operator ID from WebCurrentOperator
                    try
                    {
                        //close it temporarily
                        ev.setOperatorID(CurrentOperator.GetCurrentOperatorID(request));
                        ev.setRemoteHostAddress(request.getRemoteHost());
                    }
                    catch(Exception e)
                    {
                        System.err.println("RequestProcessor: can not get current operator dto in the relevant request");
                    }

                }

               HttpSession session= request.getSession();
               ComponentManager sl = (ComponentManager)(session.getAttribute(WebKeys.COMPONENT_MANAGER));
               WebController wcc =  sl.getWebController(request.getSession());

               try
               {
                   eventResponse = wcc.handleEvent(ev, request.getSession());
               }
               catch (CommandException ex)
               {
                  WebPrint.SetRequestExceptionErrorInfo("RequestProcessor -- CommandException", request, ex);
               }
               catch (Exception ex)
               {
                  WebPrint.SetRequestExceptionErrorInfo("RequestProcessor", request, ex);
               }

           }
           action.doEnd(request, eventResponse);
        }
    }

    /**
     * This method load the necessary HTMLAction class necessary to process a the
     * request for the specified URL.
     */

    private WebAction getAction(URLMapping urlMapping) {
        WebAction handler = null;
        String actionClassString = null;
        if (urlMapping != null) {
            actionClassString = urlMapping.getWebAction();
            if (urlMapping.isAction()) {
                try {
                    handler = (WebAction)getClass().getClassLoader().loadClass(actionClassString).newInstance();
                } catch (Exception ex) {
                    System.err.println("RequestProcessor caught loading action: " + ex);
                }
            }
        }
        return handler;
    }
}

