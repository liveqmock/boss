package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @version 1.0
 */


import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.wrap.customer.Customer2AddressWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryCustomerPrintWebAction extends QueryWebAction {
    protected int getCurrentCustomerID(Object obj) {
        //if return 0,
        if (obj == null) return 0;

        if (!(obj instanceof Customer2AddressWrap)) return 0;

        Customer2AddressWrap wrap = (Customer2AddressWrap) obj;

        return wrap.getCustDto().getCustomerID();


    }
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
		
        //¿Í»§Ö¤ºÅ
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID") ) );
        	//theDTO.setNo(request.getParameter("txtCustomerID"));
        
        
        
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER);
        //return null;
        
       

    }
    
    
    
}