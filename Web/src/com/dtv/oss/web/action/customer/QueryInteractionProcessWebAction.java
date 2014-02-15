package com.dtv.oss.web.action.customer;

/**
 * author :david.Yang
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

public class QueryInteractionProcessWebAction extends QueryWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO dto = new CommonQueryConditionDTO();

        if (WebUtil.StringHaveContent(request.getParameter("txtID")))
            dto.setSpareStr1(request.getParameter("txtID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCsiID")))
            dto.setSpareStr2(request.getParameter("txtCsiID"));

        if (WebUtil.StringHaveContent(request.getParameter("txtCreateOperatorId")))
            dto.setSpareStr3(request.getParameter("txtCreateOperatorId"));

        return new CsrQueryEJBEvent(dto, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CSI_PROCESS_LOG);
    }

}