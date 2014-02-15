package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * �ʻ�Ԥ��ͳ��
 * author     ��Jason.Zhou 
 * date       : 2007-1-12
 * description:
 * @author 250713z
 *
 */
public class AccountOweStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO��װ����˵��
		//------------------���ڿͻ�ͳ��-------
		//SpareStr1:�ͻ�����
		//SpareStr2:�ʻ�����
		//SpareStr3:���ѷ�ʽ
		//SpareStr4:����
		//SpareStr5:�ʻ�״̬
		//SpareStr6:����״̬
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2
		//SpareTime3:Ƿ��ʱ��1
		//SpareTime4:Ƿ��ʱ��2
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr1:�ͻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			dto.setSpareStr1(request.getParameter("txtCustomerType"));
		//SpareStr2:�ʻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtAccountType")))
			dto.setSpareStr2(request.getParameter("txtAccountType"));
		//SpareStr3:���ѷ�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtMopID")))
			dto.setSpareStr3(request.getParameter("txtMopID"));
		//SpareStr4:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
		//SpareStr5:�ʻ�״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr5(request.getParameter("txtStatus"));
		//SpareStr6:����״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtBankAccountStatus")))
			dto.setSpareStr6(request.getParameter("txtBankAccountStatus"));
		
		//SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	
		//SpareTime1:Ƿ��ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtOweTime1")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtOweTime1")));
		//SpareTime2:Ƿ��ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtOweTime2")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtOweTime2")));		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_OWE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
