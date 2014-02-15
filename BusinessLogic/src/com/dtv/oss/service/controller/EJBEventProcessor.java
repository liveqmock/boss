package com.dtv.oss.service.controller;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.ejb.SessionContext;

import java.util.HashMap;

// waf imports
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.controller.exception.EJBEventException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;

//import com.dtv.oss.domain.csr.CustomerLocalHome;
/**
 * This class is a responsible for processing Events recieved from the
 * client tier. Af part of the WAF framework the events are generated
 * by web actions.
 *
 * The State Machine ties all EJB components together dynamically at
 * runtime thus providing support for reusable components.
 *
 * This class should not be updated to handle various event types.
 * This class will use ActionHandlers to handle events that require
 * processing beyond the scope of this class.
 *
 * The mapping of the event names to handlers is mangaged by the JNDI
 * key contained in the Event:getEventName() which is looked up from
 * an environment entry located in the EJB Deployment descriptor of the
 * EJBClientController. A second option to event handling is to do so
 * in the XML file.
 *
 * State may be stored in the attributeMap
 *
 *
 */
public class EJBEventProcessor implements java.io.Serializable {

    private HashMap attributeMap;
    private HashMap actionMap;
    private SessionContext sc;
    private HomeFactory factory = null;
    private Context ctx;

    public EJBEventProcessor(SessionContext sc){
        this.sc = sc;
        attributeMap = new HashMap();
        actionMap = new HashMap();
        try {
            factory = HomeFactory.getFactory();
            ctx = new InitialContext();

        }catch(HomeFactoryException fe) {
            System.out.println("Create HomeFactory instance error. Check it!");
            fe.printStackTrace();
        }catch(NamingException ne) {
            System.out.println("Create InitialContext instance error. Check it!");
            ne.printStackTrace();
        }
    }

    public CommandResponse processEvent(EJBEvent ev) throws EJBEventException,CommandException {
        String commandName = ev.getEJBCommandClassName();
        System.out.println("Command name is:" + commandName + ". Loading ...");
        CommandResponse response = null;

        if (commandName  != null) {
            Command command = null;
            try {
                 if (actionMap.get(commandName) != null) {
                    command = (Command)actionMap.get(commandName);
                 } else {
                     command = (Command)Class.forName(commandName).newInstance();
                     command.setVerbose(true);
                     actionMap.put(commandName, command);
             }
            } catch (Exception ex) {
                System.err.println("EJBEventProcessor: error loading " + commandName + " :" + ex);
                throw new EJBEventException("EJBEventProcessor: error loading " + commandName + " :" + ex);
            }
            if (command != null) {
                try {
                    System.out.println("Call " + command.getClass().getName() + " init()");
	                command.init(this, ctx);
	                // do the magic
	                command.doStart();
	                response = command.execute(ev);
	                command.doEnd(ev);
                }catch(CommandException ce) {
                	try {sc.setRollbackOnly();} catch(Exception re) {}
                    System.err.println("EJBEventProcessor: error executing " + commandName);
                    throw ce;
                }catch(Throwable e) {
                    try {sc.setRollbackOnly();} catch(Exception re) {}
                    e.printStackTrace();
                    throw new CommandException("系统错误，请检查系统LOG输出.");
                }
            }
        }
        return response;
    }

    public void setAttribute(String key, Object value) {
        attributeMap.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributeMap.get(key);
    }

    public SessionContext getSessionContext() {
        return sc;
    }

}

