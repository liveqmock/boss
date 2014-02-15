package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Digivision</p>
 * <p>Description: �ͻ����Ի����ʲ�ѯ</p>
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
		//�ͻ�id
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		//ҵ���ʻ�id
		if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
			dto.setSpareStr1(request.getParameter("txtServiceAccountID"));
		//��Ʒid(ʵ�ʾ��ǿͻ���Ʒid��������Ĳ�����and�Ĺ�ϵ)
		if (WebUtil.StringHaveContent(request.getParameter("txtCustProductID")))
			dto.setSpareStr2(request.getParameter("txtCustProductID"));
		//�ͻ���Ʒid
		if (WebUtil.StringHaveContent(request.getParameter("txtPsID")))
			dto.setSpareStr3(request.getParameter("txtPsID"));
		else if (WebUtil.StringHaveContent(request.getParameter("txtPSID")))
			dto.setSpareStr3(request.getParameter("txtPSID"));
		
		return new CsrQueryEJBEvent(dto, pageFrom, pageTo,
				CsrQueryEJBEvent.QUERY_CUSTOMER_BILLING_RULE);

	}

}