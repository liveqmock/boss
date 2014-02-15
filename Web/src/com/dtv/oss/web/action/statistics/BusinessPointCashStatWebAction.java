package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class BusinessPointCashStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		/**
		 * DTO封装参数说明
		 * ------------------营业点收费统计-------
		 * date       : 2007-1-15
		 * description:
		 * SpareStr4:客户类型
		 * SpareStr3:所在机构
		 * SpareTime1:支付时间1
		 * SpareTime2:支付时间2
		 * @author 
		 *
		 */

		CommonQueryConditionDTO	theDTO = new CommonQueryConditionDTO();
//		组织和区域不同同时被选择
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("统计方式参数错误：组织和区域不能同时被选择！");
		//SpareStr1:统计方式
		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
			theDTO.setSpareStr1(request.getParameter("selStatType"));
		else
			throw new WebActionException("统计方式参数错误:统计方式字段不能为空！");
		//SpareStr2:所在区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			theDTO.setSpareStr2(request.getParameter("txtDistrictID"));	
		//SpareStr3:所在收费机构
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			theDTO.setSpareStr3(request.getParameter("txtOrgID"));
		//SpareStr4:客户类型
        if (WebUtil.StringHaveContent(request.getParameter("txtCustType")))
            theDTO.setSpareStr4(request.getParameter("txtCustType"));
        
        //SpareStr5:帐户类型
        if (WebUtil.StringHaveContent(request.getParameter("txtAccountType")))
            theDTO.setSpareStr5(request.getParameter("txtAccountType"));
        
        //SpareStr6:收费类型
        if (WebUtil.StringHaveContent(request.getParameter("txtPayType")))
            theDTO.setSpareStr6(request.getParameter("txtPayType"));
        //SpareStr7:支付方式
        if (WebUtil.StringHaveContent(request.getParameter("txtMOPId")))
            theDTO.setSpareStr7(request.getParameter("txtMOPId"));
        
        
        //SpareTime1:支付时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:支付时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	

		return new StatQueryEJBEvent(theDTO,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_POINT_CASH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
