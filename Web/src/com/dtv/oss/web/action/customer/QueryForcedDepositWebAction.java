package com.dtv.oss.web.action.customer;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
 
import com.dtv.oss.dto.ForcedDepositDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2004 </p>
 * <p> Company: Digivision</p>
 * User: thurm zhang
 * Date: 2004-12-31
 * Time: 17:12:29
 * To change this template use File | Settings | File Templates.
 */
public class QueryForcedDepositWebAction extends QueryWebAction {
    protected EJBEvent encapsulateData (HttpServletRequest request) throws WebActionException {
        ForcedDepositDTO dto = new ForcedDepositDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))) {
            dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo"))) {
            dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
        }
        
        //dto.setStatus();
        return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_FORCED_DEPOSIT);
    }
}
