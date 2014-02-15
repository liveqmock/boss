package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 产品包销售统计
 * date       : 2007-1-18
 * description:
 * @author  
 *
 */
public class ProductpackageSellStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于 产品包销售统计-------
		//SpareStr1:统计方式
		//SpareStr2:所在区域
		//SpareStr3:客户类型
		//SpareStr4:所在销售机构
		//SpareStr5:产品包类型
		//SpareTime1:销售时间1
		//SpareTime2:销售时间2
		
		//组织和区域不同同时被选择
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("统计方式参数错误：组织和区域不能同时被选择！");
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		//SpareStr1:统计方式
		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
			dto.setSpareStr1(request.getParameter("selStatType"));
		else
			throw new WebActionException("统计方式参数错误:统计方式字段不能为空！");
		//SpareStr2:所在区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr2(request.getParameter("txtDistrictID"));	
		//SpareStr3:客户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr3(request.getParameter("txtCustType"));
		//SpareStr4:所在销售机构
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr4(request.getParameter("txtOrgID"));
		//SpareStr5:产品包类型
		if(WebUtil.StringHaveContent(request.getParameter("txtPackageType")))
			dto.setSpareStr5(request.getParameter("txtPackageType"));
		//SpareTime1:销售时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:销售时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_PRODUCTPACKAGESELL);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
