package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * 帐户统计
 * author     ：Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class AccountInfoStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO封装参数说明
		//------------------用于客户统计-------
		//SpareStr1:客户类型
		//SpareStr2:帐户类型
		//SpareStr3:付费方式
		//SpareStr4:区域
		//SpareTime1:创建时间1
		//SpareTime2:创建时间2
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr1:客户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr1(request.getParameter("txtCustType"));
		//SpareStr2:帐户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctType")))
			dto.setSpareStr2(request.getParameter("txtAcctType"));
		//SpareStr3:付费方式
		if(WebUtil.StringHaveContent(request.getParameter("txtMopID")))
			dto.setSpareStr3(request.getParameter("txtMopID"));
		//SpareStr4:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
	
		//SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_INFO);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
