package com.dtv.oss.web.action.system;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.OperatorEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CurrentOperator;
import com.dtv.oss.web.util.WebCurrentOperatorData;
import com.dtv.oss.web.util.WebKeys;

public class OperatorLogoutWebAction extends GeneralWebAction{

    protected String getResponseAttributeName()
    {
        return "OperatorLogoutEJBEvent";
    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
            //to get the OperatorID
            WebCurrentOperatorData wrapOp = CurrentOperator.GetCurrentOperator(request);
            if (wrapOp==null)
            {
                throw new WebActionException("操作员连接已经断开，不需要退出！");
            }
            OperatorDTO dto= (OperatorDTO)wrapOp.getCurrentOperator();
            if (dto==null) throw new WebActionException("无法获得操作员的对象信息！");
            dto.setOperatorDesc(request.getRemoteHost());

            //Need to create OperatorLoginEvent
            return new OperatorEJBEvent(SystemEJBEvent.OPERATOR_LOGOUT, dto);

    }

    protected  void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse)
    {
        //logout success, clear session
        request.getSession().removeAttribute(WebKeys.PRIVILEGE_SESSION_NAME);
        request.getSession().removeAttribute(WebKeys.OPERATOR_SESSION_NAME);
        request.getSession().invalidate();
    }

}