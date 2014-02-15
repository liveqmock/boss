package com.dtv.oss.web.action.groupcustomer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.QueryGroupCustomerEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryContractWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
	    
		String actionType=request.getParameter("txtActionType");
		System.out.println("___actionType="+actionType);
		//���ſͻ�������ͬ��ѯ��Ĭ������.
		if(actionType!=null&&"openInfo".equalsIgnoreCase(actionType)){
			dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(TimestampUtility.getCurrentTimestamp().toString()));
			dto.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(TimestampUtility.getCurrentTimestamp().toString()));
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(TimestampUtility.getCurrentTimestamp().toString()));
		}
		//��ͬ���
	    if (WebUtil.StringHaveContent(request.getParameter("txtContractNo")))
	        dto.setSpareStr1(request.getParameter("txtContractNo"));
	    //��ͬ����
	    if (WebUtil.StringHaveContent(request.getParameter("txtName")))
	      dto.setSpareStr2(request.getParameter("txtName"));
	    
	    //��ͬ״̬
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
		      dto.setSpareStr3(request.getParameter("txtStatus"));
	        
	    //��Ч��ʼ����
	    if (WebUtil.StringHaveContent(request.getParameter("txtBeginTime")))
	    	dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtBeginTime")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtEndTime")))
	    	dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndTime")));
	    
	    //��Ч��������
	    if (WebUtil.StringHaveContent(request.getParameter("txtAvailableStartDate")))
	    	dto.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtAvailableStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAvailableEndDate")))
	    	dto.setSpareEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtAvailableEndDate")));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtGroupCustID"))){
	    	dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtGroupCustID")));
	    }
	    
	    	return new QueryGroupCustomerEJBEvent(dto, pageFrom, pageTo, QueryGroupCustomerEJBEvent.QUERY_CONTRACT);
	 
	    
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
