package com.dtv.oss.web.action.customer;

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
import com.dtv.oss.dto.wrap.customer.CustomerSAWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryGeneralCustomerWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryCustomerPhoneNoWebAction extends QueryGeneralCustomerWebAction {
    protected int getCurrentCustomerID(Object obj) {
        //if return 0,
        if (obj == null) return 0;

        if (!(obj instanceof CustomerSAWrap)) return 0;

        CustomerSAWrap wrap = (CustomerSAWrap) obj;

        return wrap.getCustDto().getCustomerID();


    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
        //µç»°ºÅÂë
        if (WebUtil.StringHaveContent(request.getParameter("txtPhoneNo")))
            theDTO.setSpareStr1(request.getParameter("txtPhoneNo")) ;

        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER_PHONENO);
        //return null;

    }
}