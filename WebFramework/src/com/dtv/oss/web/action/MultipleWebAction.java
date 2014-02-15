package com.dtv.oss.web.action;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.exception.WebActionException;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * this web action class provides a way to answer multiple actions.
 * If you want to use this action, you must take the parameter named Action for requesting
 * @author Horace Lin
 * @version 1.0
 */

public abstract class MultipleWebAction extends GeneralWebAction{
    protected int actionType = -1;

    protected int getCommonAction(String pAction)
    {
        if (pAction==null) return -1;
/*
        if (pAction.equals("update"))
            return EJBEvent.UPDATE;
        else if (pAction.equals("delete"))
            return EJBEvent.DELETE;
*/
        return -1;
    }

    protected int getSepecialAction(String pAction)
    {
        return -1;
    }

    protected int getAction(String pAction)
    {
        int result = getSepecialAction(pAction);

        if (result<0) result = getCommonAction(pAction);

        return result;
    }

    protected abstract boolean isAllowedAction();

    public void doStart(HttpServletRequest request)
    {
        super.doStart(request);
        actionType = getAction(request.getParameter("Action"));
    }

    protected boolean checkValidBeforePerform(HttpServletRequest request) throws WebActionException {
        if (!super.checkValidBeforePerform(request)) return false;

        if (!isAllowedAction())
        {
            WebPrint.PrintErrorInfo(this.getClass().getName(), "the Action Type "+request.getParameter("Action")+" is not valid!");
            return false;
        }

        WebPrint.PrintActionDebugInfo(this.getClass().getName(), actionType);

        return true;
    }


}