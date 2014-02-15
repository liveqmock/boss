package com.dtv.oss.web.action.monistat;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.MonistatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryPayFromBlankReportWebAction  extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws WebActionException {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		String actionSubmit =request.getParameter("txtAct");
	    theDTO.setSpareStr1(actionSubmit);
	    if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
	        theDTO.setSpareStr8(request.getParameter("txtDistrictID"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
	    	theDTO.setSpareStr2(request.getParameter("txtDetailAddress"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
	    	theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtName")))
	    	theDTO.setSpareStr3(request.getParameter("txtName"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
	    	theDTO.setSpareStr4(request.getParameter("txtCustomerType"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtSaStatus")))
	    	theDTO.setSpareStr5(request.getParameter("txtSaStatus"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatBeginTime")))
			theDTO.setSpareStr6(request.getParameter("txtStatBeginTime"));
		if (WebUtil.StringHaveContent(request.getParameter("txtStatEndTime")))
			theDTO.setSpareStr7(request.getParameter("txtStatEndTime"));
		
		 return new MonistatQueryEJBEvent(theDTO, pageFrom, pageTo,
	    		   MonistatQueryEJBEvent.QUERY_PAYFROMBLANK_REPORT);
	}

}
