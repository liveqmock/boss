package com.dtv.oss.web.action.groupcustomer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerQueryEJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.QueryGroupCustomerEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryGroupPackageWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ContractDTO dto=new ContractDTO();
		if(WebUtil.StringHaveContent(request.getParameter("txtContractNo"))){
			dto.setContractNo(request.getParameter("txtContractNo"));
		}		
		return new GroupCustomerQueryEJBEvent(dto,pageFrom,pageTo,QueryGroupCustomerEJBEvent.QUERY_GROUP_PACKAGELIST);
	}

}
