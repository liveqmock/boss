package com.dtv.oss.web.controller;

// J2EE imports
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.rmi.RemoteException;
import com.dtv.oss.web.util.WebKeys;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.controller.EJBController;

import com.dtv.oss.service.controller.exception.EJBEventException;
import com.dtv.oss.service.command.CommandException;

/**
 * This class is essentially just a proxy object that calls methods on the
 * EJB tier using the com.sun.j2ee.blueprints.waf.controller.ejb.ShoppingClientControllerEJB
 * object. All the methods that access the EJB are synchronized so
 * that concurrent requests do not happen to the stateful session bean.
 *
 */
public class DefaultWebController implements WebController {

    public DefaultWebController() {
    }

    /**
     * constructor for an HTTP client.
     * @param the ServletContext  object of the application
     */
    public void init(ServletContext context) {
           context.setAttribute(WebKeys.WEB_CONTROLLER, this);
    }

    /**
     * feeds the specified event to the state machine of the business logic.
     *
     * @param ese is the current event
     * @return a com.sun.j2ee.blueprints.waf.event.EventResponse object which can be extend to carry any payload.
     * @exception com.sun.j2ee.blueprints.waf.event.EventException <description>
     * @exception com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException
     */
    public CommandResponse handleEvent(EJBEvent ev, HttpSession session)
        throws EJBEventException, CommandException, RemoteException {
            DefaultComponentManager cm = (DefaultComponentManager)session.getAttribute(WebKeys.COMPONENT_MANAGER);
            EJBController controllerEJB =cm.getEJBController(session);
            return controllerEJB.processEJBEvent(ev);
    }

     /**
     * frees up all the resources associated with this controller and
     * destroys itself.
     */
    public void destroy(HttpSession session) {

        // call ejb remove on EJBClientController
        DefaultComponentManager cm = (DefaultComponentManager)session.getAttribute(WebKeys.COMPONENT_MANAGER);
        EJBController controllerEJB =cm.getEJBController(session);
        //try {
            if (controllerEJB != null) {
                session.removeAttribute(WebKeys.COMPONENT_MANAGER);
            	//controllerEJB.remove();
            }
        /*
        } catch(RemoveException re){
            // ignore, after all its only a remove() call!
            Debug.print(re);
        } catch(RemoteException ree) {
            Debug.print(ree);
        }*/

    }
}

