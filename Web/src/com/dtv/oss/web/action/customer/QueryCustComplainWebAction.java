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

public class QueryCustComplainWebAction extends QueryWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
            theDTO.setSpareStr1(request.getParameter("txtCustomerName")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerId")))
            theDTO.setSpareStr2(request.getParameter("txtCustomerId")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtContactPerson")))
            theDTO.setSpareStr3(request.getParameter("txtContactPerson")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
            theDTO.setSpareStr4(request.getParameter("txtContactPhone")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtComplainedOrgId")))
            theDTO.setSpareStr5(request.getParameter("txtComplainedOrgId")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtComplainedPerson")))
            theDTO.setSpareStr6(request.getParameter("txtComplainedPerson")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtDtCreateF")))
            theDTO.setSpareStr7(request.getParameter("txtDtCreateF")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtDtCreateT")))
            theDTO.setSpareStr8(request.getParameter("txtDtCreateT")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtType")))
            theDTO.setSpareStr9(request.getParameter("txtType")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
            theDTO.setSpareStr10(request.getParameter("txtStatus")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtCustComplainId")))
            theDTO.setSpareStr11(request.getParameter("txtCustComplainId")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("actiontype")))
            theDTO.setSpareStr12(request.getParameter("actiontype")) ;
        
        if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
        	theDTO.setSpareStr13(request.getParameter("txtQueryType"));

        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTCOMPLAIN);
        //return null;

    }
}