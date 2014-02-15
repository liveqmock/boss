package com.dtv.oss.web.action.logistics;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryDeviceTransferHisWebAction extends QueryWebAction{

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
                theDTO.setSpareStr1(request.getParameter("txtBatchID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtAction")))
                theDTO.setSpareStr2(request.getParameter("txtAction"));
        if (WebUtil.StringHaveContent(request.getParameter("txtFromID")))
                theDTO.setSpareStr3(request.getParameter("txtFromID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtFromType")))
                theDTO.setSpareStr4(request.getParameter("txtFromType"));
        if (WebUtil.StringHaveContent(request.getParameter("txtToID")))
                theDTO.setSpareStr5(request.getParameter("txtToID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtToType")))
                theDTO.setSpareStr6(request.getParameter("txtToType"));
       
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeBegin")))
                theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTimeBegin")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeEnd")))
                theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTimeEnd")));
        
        if (WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
            theDTO.setSpareStr9(request.getParameter("txtDeviceID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtSerialNoBegin")))
        	theDTO.setBeginStr(request.getParameter("txtSerialNoBegin").trim());
       
        if (WebUtil.StringHaveContent(request.getParameter("txtBatchNo")))
            theDTO.setSpareStr10(request.getParameter("txtBatchNo"));
        
        if (WebUtil.StringHaveContent(request.getParameter("txtStatusReason")))
            theDTO.setSpareStr11(request.getParameter("txtStatusReason"));

        return new LogisticsQueryEJBEvent(theDTO, pageFrom, pageTo,
                                             LogisticsQueryEJBEvent.QUERY_TYPE_DEVICETRANS);
    }

}