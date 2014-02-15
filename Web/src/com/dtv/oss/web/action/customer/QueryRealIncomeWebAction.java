package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryRealIncomeWebAction extends QueryWebAction {

	CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		Map map = new LinkedHashMap();
        //SpareTime1:创建起始时间
		//SpareTime2:创建截止时间
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDatePart"))){
	        dto.setSpareTime1(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateStartDatePart"),
					request.getParameter("txtCreateStartHourPart")
					));
	        map.put("收费起始时间", 
	        		request.getParameter("txtCreateStartDatePart") +
					((request.getParameter("txtCreateStartHourPart") ==null || request.getParameter("txtCreateStartHourPart").equals("")) 
							             ? "" 
							             : "("+request.getParameter("txtCreateStartHourPart")+"时)")
					);
	        
	    }
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtEndDatePart"))){
	        dto.setSpareTime2(WebUtil.StringToTimestampDefaultDayEnd(
	        		request.getParameter("txtEndDatePart"),
					request.getParameter("txtEndHourPart")
					));
	        map.put("收费截止时间", 
	        		request.getParameter("txtEndDatePart") +
					((request.getParameter("txtEndHourPart") ==null || request.getParameter("txtEndHourPart").equals("")) 
							              ? "" 
							              : "("+request.getParameter("txtEndHourPart")+"时)")
					);
	    }
		
		//SpareStr2:客户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID"))){
			map.put("客户证号",request.getParameter("txtCustID"));
			dto.setSpareStr2(request.getParameter("txtCustID"));
		}
		//SpareStr3:帐户号
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID"))){
			map.put("帐户号",request.getParameter("txtAcctID"));
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		}
        // SpareStr18:费用类型
		if(WebUtil.StringHaveContent(request.getParameter("txtFeeType"))){
			map.put("费用类型",Postern.getHashValueByNameKey("SET_F_BRFEETYPE",request.getParameter("txtFeeType")));
			dto.setSpareStr18(request.getParameter("txtFeeType"));
		}
		
        // SpareStr8:帐目类型
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctItemTypeID"))){
			map.put("帐目类型",Postern.getAllFeeType().get(request.getParameter("txtAcctItemTypeID")));
			dto.setSpareStr8(request.getParameter("txtAcctItemTypeID"));	
		}
			
        // SpareStr6:组织（收款组织）
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID"))){
			map.put("收费机构",Postern.getOrganizationDesc(WebUtil.StringToInt(request.getParameter("txtOrgID"))));
			dto.setSpareStr6(request.getParameter("txtOrgID"));
		}
        //  收款人operatorid
	    if (WebUtil.StringHaveContent(request.getParameter("txtCollectorID"))){
	    	map.put("收款人",request.getParameter("txtCollectorName"));
			dto.setSpareStr19(request.getParameter("txtCollectorID"));
	    }
			    
        //	  SpareStr4:客户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType"))){
			map.put("客户类型", Postern.getHashValueByNameKey("SET_C_CUSTOMERTYPE",request.getParameter("txtCustomerType")));
			dto.setSpareStr4(request.getParameter("txtCustomerType"));
		}
		
        //	  SpareStr5:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID"))){
			map.put("客户所在区域", request.getParameter("txtCountyDesc"));
			dto.setSpareStr5(request.getParameter("txtDistrictID"));
		}
		
		dto.setSpareStr7(request.getParameter("txtActionType"));
		
		//票据编号
		if(WebUtil.StringHaveContent(request.getParameter("txtTicketNo"))){
			dto.setSpareStr9(request.getParameter("txtTicketNo"));
		}
		
		request.setAttribute("RealIncomePrintInfo", map);
		
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_CASH_FLOW_COUNT);
	}

	
}
