package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class DeviceDaySalesStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		CommonQueryConditionDTO	theDTO = new CommonQueryConditionDTO();
		//��֯������ͬͬʱ��ѡ��
		//if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
		//		WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
		//	throw new WebActionException("ͳ�Ʒ�ʽ����������֯��������ͬʱ��ѡ��");
		
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			theDTO.setSpareStr3(request.getParameter("txtOrgID"));
		
        //����
		if(WebUtil.StringHaveContent(request.getParameter("txtFillDate")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtFillDate")));
		
		return new StatQueryEJBEvent(theDTO,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_DAY_SALES);
	}

}
