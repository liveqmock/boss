package com.dtv.oss.web.action.monistat;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.MonistatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryFreeTaxiReportWebAction extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws WebActionException {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		String actionSubmit =request.getParameter("txtAct");
	    theDTO.setSpareStr1(actionSubmit);
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
	        theDTO.setSpareStr2(request.getParameter("txtDistrictID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtpayStyle")))
	        theDTO.setSpareStr3(request.getParameter("txtpayStyle"));  
		if (WebUtil.StringHaveContent(request.getParameter("txtStatBeginTime")))
			theDTO.setSpareStr4(request.getParameter("txtStatBeginTime"));
		if (WebUtil.StringHaveContent(request.getParameter("txtStatEndTime")))
			theDTO.setSpareStr5(request.getParameter("txtStatEndTime"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCollectorID")))
			theDTO.setSpareStr6(request.getParameter("txtCollectorID"));
	       
		  return new MonistatQueryEJBEvent(theDTO, pageFrom, pageTo,
	    		   MonistatQueryEJBEvent.QUERY_FREE_TAXI_REPORT);
	}

}
