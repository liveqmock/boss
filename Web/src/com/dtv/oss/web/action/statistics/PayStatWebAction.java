package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class PayStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		/**
		 * DTO封装参数说明
		 * ------------------支付统计/预存统计-------
		 * date       : 2007-1-15
		 * description:
		 * SpareStr1:客户类型
		 * SpareStr2:所在区域
		 * SpareStr3:帐户类型
		 * SpareStr4:付费类型
		 * SpareStr5:页面  本方法支付统计/预存统计共用
		 * SpareTime1:创建起始时间
		 * SpareTime2:创建结束时间
		 * @author 
		 *
		 */

		CommonQueryConditionDTO	theDTO = new CommonQueryConditionDTO();
		
		//SpareStr1:客户类型
        if (WebUtil.StringHaveContent(request.getParameter("txtCustType")))
            theDTO.setSpareStr1(request.getParameter("txtCustType"));
        //SpareStr2:所在区域
        if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
            theDTO.setSpareStr2(request.getParameter("txtDistrictID"));
        //SpareStr3:帐户类型
        if (WebUtil.StringHaveContent(request.getParameter("txtAcctType")))
            theDTO.setSpareStr3(request.getParameter("txtAcctType"));
        //SpareStr4:付费类型
        if (WebUtil.StringHaveContent(request.getParameter("txtMOP")))
            theDTO.setSpareStr4(request.getParameter("txtMOP"));
        //SpareStr5:页面
        if (WebUtil.StringHaveContent(request.getParameter("txtPage")))
            theDTO.setSpareStr5(request.getParameter("txtPage"));
        //SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	

		return new StatQueryEJBEvent(theDTO,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_PAY);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
