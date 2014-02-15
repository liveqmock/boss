package com.dtv.oss.web.action.system;

import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.service.ejbevent.system.OperatorEJBEvent;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.dto.OperatorDTO;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by .
 * User: 240913z
 * Date: 2004-9-16
 * Time: 9:18:56
 * To change this template use File | Settings | File Templates.
 */
public class OperatorChangePwdWebAction extends GeneralWebAction {


    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        OperatorDTO operatorinfo = new OperatorDTO();
        operatorinfo.setLoginID(request.getParameter("loginID"));
        operatorinfo.setLoginPwd(request.getParameter("oldpwd"));
        operatorinfo.setOperatorName(request.getParameter("newpwd"));
        operatorinfo.setOperatorDesc(request.getRemoteHost());

        return new OperatorEJBEvent(SystemEJBEvent.OPERATOR_CHANGE_PWD, operatorinfo);
    }

    protected void afterSuccessfulResponse(HttpServletRequest request, CommandResponse commandResponse) {
        OperatorDTO operatorinfo = null;
        operatorinfo = (OperatorDTO) commandResponse.getPayload();
        if (operatorinfo == null) {
            request.setAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE, new Throwable("‘≠√‹¬Î ‰»Î¥ÌŒÛ£°"));
        }
    }


}
