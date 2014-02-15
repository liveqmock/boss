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

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryDeviceTransferHisDetailWebAction extends QueryWebAction{

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
        com.dtv.oss.dto.DeviceTransitionDetailDTO theDTO = new com.dtv.oss.dto.DeviceTransitionDetailDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
                theDTO.setBatchID(WebUtil.StringToInt(request.getParameter("txtBatchID")));

        return new LogisticsQueryEJBEvent(theDTO, pageFrom, pageTo,
                                             LogisticsQueryEJBEvent.QUERY_TYPE_DEVICETRANSDETAIL);

    }

}