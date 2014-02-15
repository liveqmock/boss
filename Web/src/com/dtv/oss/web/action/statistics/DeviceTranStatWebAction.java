package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * 设备流转
 * author     ：Jason.Zhou 
 * date       : 2007-1-16
 * description:
 * @author 250713z
 *
 */
public class DeviceTranStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO封装参数说明
		//------------------用于客户统计-------
		//SpareStr1:流转类型
		//SpareStr2:设备类型
		//SpareTime1:流转时间1
		//SpareTime2:流转时间2
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr1:流转类型
		if(WebUtil.StringHaveContent(request.getParameter("txtAction")))
			dto.setSpareStr1(request.getParameter("txtAction"));
		//SpareStr2:设备类型
		if(WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
			dto.setSpareStr2(request.getParameter("txtDeviceModel"));
		//SpareTime1:流转时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:流转时间2
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
