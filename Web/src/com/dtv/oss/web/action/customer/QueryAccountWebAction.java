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

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.wrap.customer.Account2AddressWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryAccountWebAction extends QueryWebAction{
  protected int getCurrentCustomerID(Object obj){
    //if return 0,

    if (obj==null) return 0;

    if (!(obj instanceof Account2AddressWrap)) return 0;

    Account2AddressWrap wrap = (Account2AddressWrap)obj;

    return wrap.getAcctDto().getCustomerID();

}

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
    	AccountDTO theDTO = new AccountDTO();
      if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
        theDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtAccountStatus")))
          theDTO.setStatus(request.getParameter("txtAccountStatus"));

      return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_ACCOUNT);

    }

}