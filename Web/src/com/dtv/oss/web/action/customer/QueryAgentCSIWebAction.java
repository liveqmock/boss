package com.dtv.oss.web.action.customer;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import javax.servlet.http.HttpServletRequest;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company: Digivision</p>
 * Author: david.Yang
 */
public class QueryAgentCSIWebAction extends QueryWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtCounty"))) {
            dto.setStreet(WebUtil.StringToInt(request.getParameter("txtCounty")));
        }
       // if (WebUtil.StringHaveContent(request.getParameter("txtAgentDealer"))){
       // 	dto.setSpareStr6(request.getParameter("txtAgentDealer"));
       // }
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
    		dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
    		dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
        dto.setType(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK);
        dto.setSpareStr26("agentBk");
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
        	dto.setStatus(request.getParameter("txtStatus"));
        return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_SIMPLECUSTSERVICEINTERACTION);
    }
}
