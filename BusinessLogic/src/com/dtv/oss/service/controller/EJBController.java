package com.dtv.oss.service.controller;

import java.rmi.*;

import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.controller.exception.EJBEventException;
import com.dtv.oss.service.ejbevent.EJBEvent;

public interface EJBController extends javax.ejb.EJBObject {
    public CommandResponse processEJBEvent(EJBEvent aEvent) throws EJBEventException, CommandException, RemoteException;
}