package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 开户情况统计
 * author     ：Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class OtherCSIStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于其他受理情况统计-------
		//SpareStr1:统计方式
		//SpareStr2:用户类型
		//SpareStr3:组织
		//SpareStr4:区域
		//SpareTime1:创建时间1
		//SpareTime2:创建时间2
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//组织和区域不同同时被选择
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("统计方式参数错误：组织和区域不能同时被选择！");
			
		//SpareStr1:统计方式
		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
			dto.setSpareStr1(request.getParameter("selStatType"));
		else
			throw new WebActionException("统计方式参数错误:统计方式字段不能为空！");
		
		//SpareStr2:用户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr2(request.getParameter("txtCustType"));
		
		//SpareStr3:组织
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr3(request.getParameter("txtOrgID"));
		//SpareStr4:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
	
		//SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtStartDate")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartDate")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtEndDate")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndDate")));
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_OTHER_CSI);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
