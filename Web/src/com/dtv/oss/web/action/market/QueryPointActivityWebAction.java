package com.dtv.oss.web.action.market;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
 
public class QueryPointActivityWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
	    
		// �ͻ���ǰ���п��û���
	    if (WebUtil.StringHaveContent(request.getParameter("txtCurrentPoints")))
	        dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCurrentPoints")));
	    // �ͻ�����
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
	      dto.setSpareStr2(request.getParameter("txtCustomerType"));
	    //�ID
	    if (WebUtil.StringHaveContent(request.getParameter("txtActivityID")))
		      dto.setStreet(WebUtil.StringToInt(request.getParameter("txtActivityID")));
	    //���õ��촦���ϵͳʱ�䣬Ϊ�˱Ƚϻ�Ŀ�ʼʱ�������ʱ��
	    dto.setSpareTime1(new Timestamp(System.currentTimeMillis()));
	    return new CsrQueryEJBEvent(dto, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_POINTS_ACTIVITY);
	    
	}

	 

}
