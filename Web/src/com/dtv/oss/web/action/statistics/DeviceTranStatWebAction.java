package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * �豸��ת
 * author     ��Jason.Zhou 
 * date       : 2007-1-16
 * description:
 * @author 250713z
 *
 */
public class DeviceTranStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO��װ����˵��
		//------------------���ڿͻ�ͳ��-------
		//SpareStr1:��ת����
		//SpareStr2:�豸����
		//SpareTime1:��תʱ��1
		//SpareTime2:��תʱ��2
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr1:��ת����
		if(WebUtil.StringHaveContent(request.getParameter("txtAction")))
			dto.setSpareStr1(request.getParameter("txtAction"));
		//SpareStr2:�豸����
		if(WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
			dto.setSpareStr2(request.getParameter("txtDeviceModel"));
		//SpareTime1:��תʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:��תʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_TRAN);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
