package com.dtv.oss.web.action.monistat;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.MonistatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;

public class QueryFinancialReportWebAction extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws WebActionException {
		String txtDistrictID =request.getParameter("txtDistrictID");
		String txtSessionName =request.getParameter("txtSessionName");
		String actionSubmit =request.getParameter("txtAct");
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		theDTO.setSpareStr1(txtDistrictID);
		theDTO.setSpareStr2(txtSessionName);
		theDTO.setSpareStr3(actionSubmit);
		
		return new MonistatQueryEJBEvent(theDTO, pageFrom, pageTo,
	    		   MonistatQueryEJBEvent.QUERY_FINANCIAL_REPORT);
	}

}
