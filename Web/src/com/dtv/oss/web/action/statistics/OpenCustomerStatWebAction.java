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
public class OpenCustomerStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于开户情况情况统计-------
		//SpareStr1:统计方式
		//SpareStr2:分类方式
		//SpareStr3:组织
		//SpareStr4:区域
		//SpareStr5:客户类型
		//SpareStr6:安装方式
		//SpareStr7:受理状态
		//SpareStr8:来源渠道
		//SpareStr9:来源渠道ID
		//SpareStr10:统计类型
		//SpareTime1:创建时间1
		//SpareTime2:创建时间2
		//SpareTime3:完成时间1
		//SpareTime4:完成时间2

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
		//SpareStr2:分类方式
		if(WebUtil.StringHaveContent(request.getParameter("selSortType")))
			dto.setSpareStr2(request.getParameter("selSortType"));
		else
			throw new WebActionException("统计方式参数错误：分类方式字段不能为空！");
		//SpareStr3:组织
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr3(request.getParameter("txtOrgID"));
		//SpareStr4:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
		//SpareStr5:客户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr5(request.getParameter("txtCustType"));
		//SpareStr6:安装方式
		if(WebUtil.StringHaveContent(request.getParameter("txtInstallationType")))
			dto.setSpareStr6(request.getParameter("txtInstallationType"));
		//SpareStr7:受理状态
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr7(request.getParameter("txtStatus"));
		//SpareStr8:来源渠道
		if(WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
			dto.setSpareStr8(request.getParameter("txtOpenSourceType"));
		//SpareStr9:来源渠道ID
		if(WebUtil.StringHaveContent(request.getParameter("txtOpenSourceID")))
			dto.setSpareStr9(request.getParameter("txtOpenSourceID"));
		//SpareStr10:统计类型
		if(WebUtil.StringHaveContent(request.getParameter("txtOpenType")))
			dto.setSpareStr10(request.getParameter("txtOpenType"));
		//SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//SpareTime3:完成时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime3")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime3")));
		//SpareTime4:完成时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime4")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime4")));

		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_OPEN_CUSTOMER);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
