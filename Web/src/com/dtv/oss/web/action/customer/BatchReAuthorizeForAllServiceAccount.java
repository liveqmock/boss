package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class BatchReAuthorizeForAllServiceAccount extends GeneralWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws Exception {
		String customer = request.getParameter("txtCustomerID");
		CustomerEJBEvent event=new CustomerEJBEvent(EJBEvent.REAUTHORIZE_FOR_SA_BY_CUSTOMER);
		if (WebUtil.StringHaveContent(customer)) {
			CustomerDTO custDTO = new CustomerDTO();
			custDTO.setCustomerID (WebUtil.StringToInt(customer));
			event.setCustomerDTO (custDTO);
		}
		return event;
	}

}
