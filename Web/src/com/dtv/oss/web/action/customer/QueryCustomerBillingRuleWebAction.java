package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Digivision</p>
 * <p>Description: 客户个性化费率查询</p>
 * @author chaoqiu
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryGeneralCustomerWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryCustomerBillingRuleWebAction extends QueryGeneralCustomerWebAction{

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
    	CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
		//客户id
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		//业务帐户id
		if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
			dto.setSpareStr1(request.getParameter("txtServiceAccountID"));
		//产品id(实际就是客户产品id，与下面的参数是and的关系)
		if (WebUtil.StringHaveContent(request.getParameter("txtCustProductID")))
			dto.setSpareStr2(request.getParameter("txtCustProductID"));
		//客户产品id
		if (WebUtil.StringHaveContent(request.getParameter("txtPsID")))
			dto.setSpareStr3(request.getParameter("txtPsID"));
		else if (WebUtil.StringHaveContent(request.getParameter("txtPSID")))
			dto.setSpareStr3(request.getParameter("txtPSID"));
		
		return new CsrQueryEJBEvent(dto, pageFrom, pageTo,
				CsrQueryEJBEvent.QUERY_CUSTOMER_BILLING_RULE);

	}

}