package com.dtv.oss.web.controller;

// J2EE Imports
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.rmi.RemoteException;

// WAF imports
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.controller.exception.EJBEventException;
import com.dtv.oss.service.command.CommandException;

/**
 * This class is essentially just a proxy object that calls methods on the
 * EJB tier using the com.sun.j2ee.blueprints.waf.controller.ejb.ShoppingClientControllerEJB
 * object. All the methods that access the EJB are synchronized so
 * that concurrent requests do not happen to the stateful session bean.
 */
public interface WebController extends java.io.Serializable {


    /**
     * constructor for an HTTP client.
     * @param the  ServletContext object of the application
     */
    public void init(ServletContext context);


    /**
     * feeds the specified event to the state machine of the business logic.
     *
     * @param ev is the current event
     * @return an com.sun.j2ee.blueprints.waf.event.EventResponse resulting in the
     *         processing of this event.
     * @exception com.sun.j2ee.blueprints.waf.event.EventException <description>
     * @exception com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException
     */
    public  CommandResponse handleEvent(EJBEvent ev, HttpSession session) throws EJBEventException,CommandException, RemoteException;

     /**
     * frees up all the resources associated with this controller and
     * destroys itself.
     */
    public  void destroy(HttpSession session);
}

