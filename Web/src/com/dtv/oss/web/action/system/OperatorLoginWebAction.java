package com.dtv.oss.web.action.system;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import java.util.*;
import javax.servlet.http.HttpServletRequest;
//import oss class
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.commandresponse.CommandResponse;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.service.ejbevent.system.OperatorEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.dto.wrap.system.OperatorLoginResult;
import com.dtv.oss.web.util.*;

public class OperatorLoginWebAction extends GeneralWebAction
{
    protected String getResponseAttributeName()
    {
        return "OperatorLoginEJBEvent";
    }

    protected boolean checkValidBeforePerform(HttpServletRequest request) throws WebActionException
    {
        if (!super.checkValidBeforePerform(request)) return false;

        if (!WebUtil.StringHaveContent(request.getParameter("txtLoginID")))
        {
            throw new WebActionException("用户名不能为空。");
        }


        if (!WebUtil.StringHaveContent(request.getParameter("txtLoginPwd")))
        {
            throw new WebActionException("口令不能为空。");
        }

        return true;
    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {

           OperatorDTO OperatorInfo = new OperatorDTO();

            Date dt= new Date();

            WebPrint.PrintPrivilegeDebugInfo("** "+dt.toString()+" Login is happening...");
            WebPrint.PrintPrivilegeDebugInfo("** Come from "+ request.getRemoteAddr());
            WebPrint.PrintPrivilegeDebugInfo("** Login name is " + request.getParameter("txtLoginID"));

            OperatorInfo.setLoginID(request.getParameter("txtLoginID"));
            OperatorInfo.setLoginPwd(request.getParameter("txtLoginPwd"));
            //借用OperatorDesc传送登陆者的主机
            OperatorInfo.setOperatorDesc(request.getRemoteHost());

            return new OperatorEJBEvent(SystemEJBEvent.OPERATOR_LOGIN, OperatorInfo);

    }

    protected  void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse)
    {
        //clear two attributes of the current session
        request.getSession().removeAttribute(WebKeys.PRIVILEGE_SESSION_NAME);
        request.getSession().removeAttribute(WebKeys.OPERATOR_SESSION_NAME);
        request.getSession().removeAttribute(WebKeys.CMS_MSOSYSTEMNAME);
        //clear session
        request.getSession().invalidate();
		//在这里设置系统登陆时显示的head名称
        request.getSession().setAttribute(WebKeys.CMS_MSOSYSTEMNAME, 
        		String.valueOf(Postern.excuteQueryBySQL("select MSOSYSTEMNAME from t_bossconfiguration")));
        //to get the payload of the command response
        OperatorLoginResult operatorLoginResult = (OperatorLoginResult)cmdResponse.getPayload();
        if (operatorLoginResult == null)
        {
            WebPrint.PrintPrivilegeDebugInfo("Can not get Current Operator Info!!!");
            return;
        }

        if (WebKeys.DEBUG_PRIVILEGE_FLAG)
        {
            Map mapPri = operatorLoginResult.getPrivileges();

            WebPrint.PrintPrivilegeDebugInfo("Operator Privilege List:");
            if (mapPri!= null)
            {
                Iterator itPri = mapPri.entrySet().iterator();
                while (itPri.hasNext())
                {
                    Map.Entry PriItem = (Map.Entry)itPri.next();
                    System.out.print(PriItem.getKey().toString()+"="+PriItem.getValue().toString()+"; ");
                }
            }

        }

        WebCurrentOperatorData curOper = new LogonWebCurrentOperator(operatorLoginResult.getOperator());
        //set two attributes of the current session according to the command response
        request.getSession().setAttribute(WebKeys.PRIVILEGE_SESSION_NAME , operatorLoginResult.getPrivileges());
        request.getSession().setAttribute(WebKeys.OPERATOR_SESSION_NAME , curOper);

    }

    protected  void afterExceptionHappen(HttpServletRequest request)
    {
        super.afterExceptionHappen(request);
        WebPrint.PrintErrorInfo(this.getClass().getName(), "** !!!There is a error happend in login");
    }


}