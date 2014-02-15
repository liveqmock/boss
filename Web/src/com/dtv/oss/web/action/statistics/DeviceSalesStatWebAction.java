package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class DeviceSalesStatWebAction  extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		/**
		 * DTO封装参数说明
		 * ------------------设备销售统计-------
		 * date       : 2008-06-13
		 * setSpareStr3:所在机构
		 * SpareTime1:起始时间1
		 * SpareTime2:截止时间2
		 * @author 
		 *
		 */

		CommonQueryConditionDTO	theDTO = new CommonQueryConditionDTO();
		//组织和区域不同同时被选择
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("统计方式参数错误：组织和区域不能同时被选择！");
		
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			theDTO.setSpareStr3(request.getParameter("txtOrgID"));
		
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			theDTO.setSpareStr4(request.getParameter("txtCustType"));
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
			theDTO.setSpareStr5(request.getParameter("txtDeviceModel"));
        
        //SpareTime1:支付时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:支付时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	
		
		return new StatQueryEJBEvent(theDTO,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_SALES);
	}

}
