package com.dtv.oss.service.controller;

import javax.ejb.*;
import java.rmi.*;

public interface EJBControllerHome extends javax.ejb.EJBHome {
    public EJBController create() throws CreateException, RemoteException;
}