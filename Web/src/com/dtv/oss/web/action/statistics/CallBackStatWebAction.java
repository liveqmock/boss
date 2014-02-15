package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 开户回访统计
 * date       : 2007-1-29
 * description:
 * @author  
 *
 */
public class CallBackStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于 开户回访统计-------
		//SpareStr1:统计方式
		//SpareStr2:回访标志
		//SpareStr3:区域
		//SpareStr4:所属组织
		//SpareStr5:安装方式
		//SpareTime1:创建时间1
		//SpareTime2:创建时间2
		//SpareTime3:回访时间1
		//SpareTime4:回访时间2
		
		//组织和区域不可同时被选择
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("统计方式参数错误：组织和区域不能同时被选择！");
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		//SpareStr1:统计方式
		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
			dto.setSpareStr1(request.getParameter("selStatType"));
		else
			throw new WebActionException("统计方式参数错误:统计方式字段不能为空！");
		//SpareStr2:回访标志
		if(WebUtil.StringHaveContent(request.getParameter("txtCallBackFlag")))
			dto.setSpareStr2(request.getParameter("txtCallBackFlag"));	
		//SpareStr3:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr3(request.getParameter("txtDistrictID"));
		//SpareStr4:所属组织
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr4(request.getParameter("txtOrgID"));
		//SpareStr5:安装方式
		if(WebUtil.StringHaveContent(request.getParameter("txtInstallationType")))
			dto.setSpareStr5(request.getParameter("txtInstallationType"));
		//SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		
		//SpareTime3:回访时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime3")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime3")));
		//SpareTime4:回访时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime4")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime4")));
		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_CALL_BACK);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
