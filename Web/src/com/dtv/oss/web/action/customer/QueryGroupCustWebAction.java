package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author fiona
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryGroupCustWebAction extends QueryWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
        if (WebUtil.StringHaveContent(request.getParameter("txtGroupCustID")))
            theDTO.setSpareStr1(request.getParameter("txtGroupCustID")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtGroupCustName")))
            theDTO.setSpareStr2(request.getParameter("txtGroupCustName")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            theDTO.setSpareStr3(request.getParameter("txtCustomerID")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            theDTO.setSpareStr4(request.getParameter("txtName"));
        
        if (WebUtil.StringHaveContent(request.getParameter("txtContractNo")))
            theDTO.setSpareStr5(request.getParameter("txtContractNo"));
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_GROUP_CUST);
        //return null;

    }
}