package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * Ѻ�����Ȩͳ��
 * author     ��Jason.Zhou 
 * date       : 2007-1-16
 * description:
 * @author 250713z
 *
 */
public class ForceDepositFutuerRightForFinaceStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO��װ����˵��
		//------------------���ڿͻ�ͳ��-------
		//SpareStr1:�ͻ�����
		//SpareStr2:����
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr1:�ͻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			dto.setSpareStr1(request.getParameter("txtCustomerType"));
		//SpareStr2:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr2(request.getParameter("txtDistrictID"));
		//SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT_FOR_FINACE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}