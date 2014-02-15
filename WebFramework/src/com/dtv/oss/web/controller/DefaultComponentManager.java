package com.dtv.oss.web.controller;

// J2EE Imports
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

// WAF Imports
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.controller.ServiceLocator;
import com.dtv.oss.web.exception.ServiceLocatorException;
import com.dtv.oss.web.util.JNDINames;
import com.dtv.oss.web.exception.GeneralFailureException;
import com.dtv.oss.web.controller.WebController;
import com.dtv.oss.service.controller.EJBControllerHome;
import com.dtv.oss.service.controller.EJBController;




/**
 * This implmentation class of the ComponentManager provides
 * access to services in the web tier and ejb tier.
 *
 */
public class DefaultComponentManager implements ComponentManager, java.io.Serializable {

    protected ServiceLocator sl = null;

    public DefaultComponentManager() {
        sl = ServiceLocator.getInstance();

        System.out.println("Construct DefaultComponentManager class");
    }

    public WebController getWebController(HttpSession session) {
        ServletContext context = session.getServletContext();
        WebController wcc =  (WebController)context.getAttribute(WebKeys.WEB_CONTROLLER);
        if ( wcc == null ) {
            try {
                //String wccClassName = sl.getString(JNDINames.DEFAULT_WEB_CONTROLLER);
                //wcc = (WebController) Beans.instantiate(this.getClass().getClassLoader(), wccClassName);
                wcc = new DefaultWebController();
                wcc.init(context);
            } /*catch (ServiceLocatorException slx) {
                throw new RuntimeException ("Cannot create bean of class WebController: " + slx);
            }*/ catch (Exception exc) {
                 throw new RuntimeException ("Cannot create bean of class WebController: " + exc);
             }
         }
         return wcc;
    }

    public EJBController getEJBController(HttpSession session) {

        EJBController ccEjb = (EJBController)session.getAttribute(WebKeys.EJB_CONTROLLER);
        if (ccEjb == null) {
            try {
               //get JNDI Name for JNDINames.EJB_CONTROLLER_EJBHOME from web.xml
              String strTmp= session.getServletContext().getInitParameter("JNDINames.EJB_CONTROLLER_EJBHOME");
              if ((strTmp!=null)&&(!strTmp.equals(""))) JNDINames.EJB_CONTROLLER_EJBHOME = strTmp;
                EJBControllerHome home = (EJBControllerHome)sl.getRemoteHome(JNDINames.EJB_CONTROLLER_EJBHOME, EJBControllerHome.class);
                ccEjb = home.create();
            } catch (CreateException ce) {
                throw new GeneralFailureException(ce.getMessage());
            } catch (ServiceLocatorException slx) {
                 throw new GeneralFailureException(slx.getMessage());
            } catch(RemoteException re) {
                throw new GeneralFailureException(re.getMessage());
            }
            session.setAttribute(WebKeys.EJB_CONTROLLER, ccEjb);
        }
        return ccEjb;
    }

    /**
     *
     * Create the WebController which in turn should create the
     * EJBClientController.
     *
     */
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sl = ServiceLocator.getInstance();
        session.setAttribute(WebKeys.COMPONENT_MANAGER, this);
    }

    /**
     *
     * Destroy the WebClientController which in turn should destroy the
     * EJBClientController.
     *
     */
    public void sessionDestroyed(HttpSessionEvent se) {
    try{
        HttpSession session = se.getSession();
        WebController wcc = getWebController(session);
        if (wcc != null) {
            wcc.destroy(session);
        }
   }
   catch(Exception exe){
   // ignore the exception
  }
    }
}


