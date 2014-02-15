package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryBatchInvoiceWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		CommonQueryConditionDTO dto=new CommonQueryConditionDTO();
		//�û�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))){
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		}
		//�û�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType"))){
			dto.setSpareStr1(request.getParameter("txtCustomerType"));
		}
		//�շ��ն�״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceAcctStatus"))){
			dto.setSpareStr4(request.getParameter("txtServiceAcctStatus"));
		}
		//������ʼ����
		if(WebUtil.StringHaveContent(request.getParameter("txtDebtPeriodTo"))){
			dto.setEndStr(request.getParameter("txtDebtPeriodTo"));
		}
		//�������ڽ�ֹ
		if(WebUtil.StringHaveContent(request.getParameter("txtDebtPeriodFrom"))){
			dto.setBeginStr(request.getParameter("txtDebtPeriodFrom"));
		}
		//��������
		if(WebUtil.StringHaveContent(request.getParameter("txtMopID"))){
			dto.setSpareStr2(request.getParameter("txtMopID"));
		}
		//��������
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID"))){
		   dto.setSpareStr5(request.getParameter("txtDistrictID"));
		}
		//��ѯ��ʽ
		if (WebUtil.StringHaveContent(request.getParameter("txtqueryStyle"))){
		   dto.setOrderField(request.getParameter("txtqueryStyle"));
		}
		//�˻��ܽ�Χ
		if (WebUtil.StringHaveContent(request.getParameter("txtAcctBalance1")))
		   dto.setSpareStr15(request.getParameter("txtAcctBalance1"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAcctBalance2")))
	       dto.setSpareStr16(request.getParameter("txtAcctBalance2"));
	    //��ϸ��ַ
	    if (WebUtil.StringHaveContent(request.getParameter("txtAddressDetail")))
	       dto.setSpareStr6(request.getParameter("txtAddressDetail"));
		//�ʻ�ID
	    if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
	       dto.setSpareStr7(request.getParameter("txtAccountID"));
	    
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_BATCH_INVOICE);
	}

}
