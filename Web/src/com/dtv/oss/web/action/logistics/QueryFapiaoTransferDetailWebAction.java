// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2008-5-19 13:17:40
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   QueryFapiaoTransferDetailWebAction.java

package com.dtv.oss.web.action.logistics;

import com.dtv.oss.dto.FapiaoTransitionDetailDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
public class QueryFapiaoTransferDetailWebAction extends QueryWebAction
{

    public QueryFapiaoTransferDetailWebAction()
    {
    }

    protected EJBEvent encapsulateData(HttpServletRequest request)
        throws WebActionException
    {
              CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
        if(WebUtil.StringHaveContent(request.getParameter("txtFapiaoSeqNo")))
                dto.setSpareStr14(request.getParameter("txtFapiaoSeqNo").trim());
        return new LogisticsQueryEJBEvent(dto, pageFrom, pageTo, LogisticsQueryEJBEvent.QUERY_TYPE_FAPIAOTRANSDETAIL);
    }
}