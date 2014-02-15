package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryScheduleWebAction extends QueryWebAction {

	/**
	 * ��ѯ���CommonQueryConditionDTO����Ĳ�����˵��
	 * 
	 * �¼����ͣ�txtActionType�� 								spareStr3
	 * �¼�ԭ��(txtEventReason) 								spareStr4
	 * �ų�ִ��ʱ��(txtExecuteDate1��txtExecuteDate2)			spareTime1��spareTime2
	 * �ų̴���ʱ�䣨txtCreateDate1��txtCreateDate2��			spareTime3��spareTime4
	 * �û�֤��(txtCustomerID)								spareStr5
	 * �ͻ�����(txtCustomerName)								spareStr6
	 * �ų�����״̬(txtStatus) 								spareStr7
	 * 
	 * ҵ���ʻ���(txtSAID) : spareStr1
	 * 
	 * �ų�������ID��(txtBatchID) spareStr2
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
	    
		//�ų�������ID
	    if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
	        dto.setSpareStr1(request.getParameter("txtBatchID"));
	    //ҵ���ʻ���
	    if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
	      dto.setSpareStr2(request.getParameter("txtServiceAccountID"));
	   //�ų�����״̬
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
	        dto.setSpareStr7(request.getParameter("txtStatus"));
	    //�ͻ�����
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
	      dto.setSpareStr6(request.getParameter("txtCustomerName"));
	    //�û�֤��
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setSpareStr5(request.getParameter("txtCustomerID"));
	    //�¼�ԭ��
	    if (WebUtil.StringHaveContent(request.getParameter("txtEventReason")))
	        dto.setSpareStr4(request.getParameter("txtEventReason"));
	    //�¼�����
	    if (WebUtil.StringHaveContent(request.getParameter("txtEventType")))
	        dto.setSpareStr3(request.getParameter("txtEventType"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtReferType")))
	        dto.setSpareStr8(request.getParameter("txtReferType"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAddress")))
	    	dto.setSpareStr9(request.getParameter("txtAddress"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
	    	dto.setDistrict(WebUtil.StringToInt(request.getParameter("txtCounty")));
	    
	        
	    //�ų�ִ��ʱ��
	    if (WebUtil.StringHaveContent(request.getParameter("txtExecuteDate1")))
	    	dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtExecuteDate1")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtExecuteDate2")))
	    	dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtExecuteDate2")));
	    
	    //�ų̴���ʱ��
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateDate1")))
	    	dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateDate1")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateDate2")))
	    	dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateDate2")));
	    
	    if("customer".equalsIgnoreCase(request.getParameter("txtActionType")))
	    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_CUSTOMER);
	    else if("all".equalsIgnoreCase(request.getParameter("txtActionType")))
	    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_ALL);
	    else if("detail".equalsIgnoreCase(request.getParameter("txtActionType")))
	    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_DETAIL);
	    else
	    	throw new WebActionException("���Ҳ�������");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
