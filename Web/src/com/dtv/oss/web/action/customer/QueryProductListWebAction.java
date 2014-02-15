package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryProductListWebAction extends QueryWebAction{
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
    	  ProductDTO theDTO = new ProductDTO();
          if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
                theDTO.setStatus(request.getParameter("txtStatus"));
          if (WebUtil.StringHaveContent(request.getParameter("txtProductClass")))
             theDTO.setProductClass(request.getParameter("txtProductClass"));
          return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_PRODUCT);
        }
    
}
