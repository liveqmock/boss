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
public class FailedBankAcctCheckStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO封装参数说明
		//------------------用于客户统计-------
		//SpareStr1:帐户状态
		//SpareStr2:客户类型
		//SpareStr4:区域
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr1:帐户状态
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr1(request.getParameter("txtStatus"));
		
		//SpareStr2:客户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			dto.setSpareStr2(request.getParameter("txtCustomerType"));
		
		//SpareStr4:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
	
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_FAILED_BANK_ACCT_CHECK);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
