package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class BatchPrepayQueryWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
	        theDTO.setSpareStr1(request.getParameter("txtDistrictID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			theDTO.setSpareStr2(request.getParameter("txtCustomerType"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			theDTO.setSpareStr3(request.getParameter("txtCustomerID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtAcctName")))
			theDTO.setSpareStr4(request.getParameter("txtAcctName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtAccountIDs")))
			theDTO.setSpareStr5(request.getParameter("txtAccountIDs"));
		if (WebUtil.StringHaveContent(request.getParameter("txtAcctBalance1")))
	        theDTO.setSpareStr15(request.getParameter("txtAcctBalance1"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAcctBalance2")))
	    	theDTO.setSpareStr16(request.getParameter("txtAcctBalance2"));
	    int txtFrom =WebUtil.StringToInt(request.getParameter("txtFrom"));
	    int txtTo   =WebUtil.StringToInt(request.getParameter("txtTo"));
        //	查询方式
		if (WebUtil.StringHaveContent(request.getParameter("txtqueryStyle"))){
			theDTO.setOrderField(request.getParameter("txtqueryStyle"));
		}
        // 收费终端状态
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceAcctStatus"))){
			theDTO.setSpareStr6(request.getParameter("txtServiceAcctStatus"));
		}
		// 详细地址
		if (WebUtil.StringHaveContent(request.getParameter("txtAddressDetail"))){
			theDTO.setSpareStr7(request.getParameter("txtAddressDetail"));
		}
		
	    return new CsrQueryEJBEvent(theDTO, txtFrom, txtTo,
				CsrQueryEJBEvent.QUERY_BATCH_PREPAY_ACCOUNT);
		
	}

}
