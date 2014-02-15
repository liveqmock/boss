package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 客户产品统计
 * author     ：Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class CustomerProductStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO封装参数说明
		//------------------用于客户产品统计-------
		//SpareStr1:区域
		//SpareStr2:客户类型
		//SpareStr3:订户类型
		//SpareStr4:客户产品状态
		//SpareTime1:开户时间1
		//SpareTime2:开户创建时间2
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr1:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr1(request.getParameter("txtDistrictID"));
		
		//SpareStr2:客户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			dto.setSpareStr2(request.getParameter("txtCustomerType"));
		//SpareStr3:订户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceType")))
			dto.setSpareStr3(request.getParameter("txtServiceType"));	
		//SpareStr4:客户产品状态
		if(WebUtil.StringHaveContent(request.getParameter("txtCPStatus")))
			dto.setSpareStr4(request.getParameter("txtCPStatus"));
		//SpareStr5:统计的产品
		if(WebUtil.StringHaveContent(request.getParameter("txtCPProductIDList")))
			dto.setSpareStr5(request.getParameter("txtCPProductIDList"));
		//SpareStr6:产品类型
		if(WebUtil.StringHaveContent(request.getParameter("txtProductClass")))
			dto.setSpareStr6(request.getParameter("txtProductClass"));
		
		//SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER_PRODUCT);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
