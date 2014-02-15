package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class SetOffStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于销帐情况统计-------
		//SpareStr1:统计方式
		//SpareStr2:组织
		//SpareStr3:区域
		//SpareStr4:实收关联类型
		//SpareTime1:销帐时间1
		//SpareTime2:销帐时间2

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//组织和区域不同同时被选择
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("统计方式参数错误：组织和区域不能同时被选择！");
			
		//SpareStr2:组织
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr2(request.getParameter("txtOrgID"));
		//SpareStr3:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr3(request.getParameter("txtDistrictID"));
		////SpareStr4:实收关联类型
		if(WebUtil.StringHaveContent(request.getParameter("txtPlusReferType")))
			dto.setSpareStr4(request.getParameter("txtPlusReferType"));
		else
			throw new WebActionException("统计方式参数错误");
		//SpareTime1:创建起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));

		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_SETOFF);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
