package com.dtv.oss.service.controller;

import javax.ejb.*;

import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.controller.exception.EJBEventException;
import com.dtv.oss.service.ejbevent.EJBEvent;

public class EJBControllerBean implements SessionBean {
    SessionContext sessionContext;
    EJBEventProcessor processor;

    public void ejbCreate() throws CreateException {
        /**@todo Complete this method*/
        processor = new EJBEventProcessor(sessionContext);
    }
    public void ejbRemove() {
        /**@todo Complete this method*/
    }
    public void ejbActivate() {
        /**@todo Complete this method*/
    }
    public void ejbPassivate() {
        /**@todo Complete this method*/
    }
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }
    public CommandResponse processEJBEvent(EJBEvent aEvent) throws EJBEventException, CommandException {
        /**@todo Complete this method*/
        System.out.println("In EJBControllerBean: processEvent()");
        return processor.processEvent(aEvent);
    }
}