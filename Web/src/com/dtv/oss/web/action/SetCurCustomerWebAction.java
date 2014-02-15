package com.dtv.oss.web.action;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.WebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CurrentCustomer;

public class SetCurCustomerWebAction extends WebAction {

    public EJBEvent perform(HttpServletRequest request) throws WebActionException {

        CurrentCustomer.UpdateCurrentCustomer(request);

        return null;

    }



}