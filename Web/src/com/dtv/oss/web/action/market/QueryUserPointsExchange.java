package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
 
public class QueryUserPointsExchange extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
	    
		// 活动id
	    if (WebUtil.StringHaveContent(request.getParameter("txtActivityId")))
	        dto.setSpareStr1(request.getParameter("txtActivityId"));
	    // 货物id
	    if (WebUtil.StringHaveContent(request.getParameter("txtGoodsId")))
	      dto.setSpareStr2(request.getParameter("txtGoodsId"));
	    //用户证号
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustId")))
		      dto.setSpareStr3(request.getParameter("txtCustId"));
	    //用户姓名
	    if (WebUtil.StringHaveContent(request.getParameter("txtName")))
		      dto.setSpareStr4(request.getParameter("txtName")); 
	    //所在区域
	    if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
		      dto.setDistrict(WebUtil.StringToInt(request.getParameter("txtCounty"))); 
	    //详细地址
	    if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
		      dto.setSpareStr5(request.getParameter("txtDetailAddress"));  
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
	        dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
	        dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate"))); 
	    return new CsrQueryEJBEvent(dto, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_POINTS_RECORD);
	    
	}

	 

}
