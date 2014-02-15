package com.dtv.oss.web.action.groupcustomer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ContractProcessLogDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.QueryGroupCustomerEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryContractLogWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		ContractProcessLogDTO	dto = new ContractProcessLogDTO();
	    
		//ºÏÍ¬±àºÅ
	    if (WebUtil.StringHaveContent(request.getParameter("txtContractNo")))
	        dto.setContractNo(request.getParameter("txtContractNo"));
	    
	  
	    
	    	return new QueryGroupCustomerEJBEvent(dto, pageFrom, pageTo, QueryGroupCustomerEJBEvent.QUERY_CONTRACT_LOG);
	 
	    
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
