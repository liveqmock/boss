package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryOweSupendWebAction  extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws WebActionException {
       CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
       if (WebUtil.StringHaveContent(request.getParameter("txtName")))
           theDTO.setSpareStr1(request.getParameter("txtName"));
       if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
    	   theDTO.setSpareStr10(request.getParameter("txtCustomerType"));
       if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
    	   theDTO.setSpareStr11(request.getParameter("txtDistrictID"));
       if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
    	   theDTO.setSpareStr12(request.getParameter("txtDetailAddress"));
       if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
    	   theDTO.setSpareStr13(request.getParameter("txtCsiCreateReason"));
       if (WebUtil.StringHaveContent(request.getParameter("txtServiceAcctStatus")))
    	   theDTO.setSpareStr14(request.getParameter("txtServiceAcctStatus"));
       if (WebUtil.StringHaveContent(request.getParameter("txtAcctBalance1")))
    	   theDTO.setSpareStr15(request.getParameter("txtAcctBalance1"));
       if (WebUtil.StringHaveContent(request.getParameter("txtAcctBalance2")))
    	   theDTO.setSpareStr16(request.getParameter("txtAcctBalance2"));
       if (WebUtil.StringHaveContent(request.getParameter("txtPauseStartDate")))
    	   theDTO.setSpareTime1(WebUtil.StringToTimestamp(request.getParameter("txtPauseStartDate")));
       if (WebUtil.StringHaveContent(request.getParameter("txtPauseEndDate")))
    	   theDTO.setSpareTime2(WebUtil.StringToTimestamp(request.getParameter("txtPauseEndDate")));
       if (WebUtil.StringHaveContent(request.getParameter("txtProductId")))   
           theDTO.setSpareStr18(request.getParameter("txtProductId"));
       if (WebUtil.StringHaveContent(request.getParameter("txtqueryStyle")))
           theDTO.setSpareStr19(request.getParameter("txtqueryStyle"));
       //¸¶·Ñ·½Ê½
       if (WebUtil.StringHaveContent(request.getParameter("txtMopID")))
    	   theDTO.setSpareStr20(request.getParameter("txtMopID"));
       
       String actionSubmit =request.getParameter("txtAct");
       theDTO.setSpareStr17(actionSubmit);
       
       return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo,
				CsrQueryEJBEvent.QUERY_OWE_SUSPEND_CUSTOMER);
	}
	
	

}
