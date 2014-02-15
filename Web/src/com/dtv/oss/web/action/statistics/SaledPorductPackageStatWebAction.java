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
public class SaledPorductPackageStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于产品包销售情况统计-------
		//SpareStr2:分类方式
		//SpareStr3:组织
		//SpareStr4:区域
		//SpareStr5:用户类型
		//SpareStr9:来源渠道ID
		//SpareTime1:创建时间1
		//SpareTime2:创建时间2
		//SpareTime3:完成时间1
		//SpareTime4:完成时间2

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr2:分类方式
		if(WebUtil.StringHaveContent(request.getParameter("selSortType")))
			dto.setSpareStr2(request.getParameter("selSortType"));
		else
			throw new WebActionException("统计方式参数错误：产品包状态字段不能为空！");

		//SpareStr4:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
		//SpareStr5:用户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr5(request.getParameter("txtCustType"));
		
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

		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_SALED_PRODUCT_PACKAGE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
