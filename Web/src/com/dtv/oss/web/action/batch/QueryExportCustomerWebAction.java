package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryExportCustomerWebAction  extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
		   dto.setSpareStr3(request.getParameter("txtDistrictID"));
	    if(WebUtil.StringHaveContent(request.getParameter("txtName")))
	       dto.setSpareStr1(request.getParameter("txtName"));
	    if(WebUtil.StringHaveContent(request.getParameter("txtCollectorID")))
	       dto.setOperator(request.getParameter("txtCollectorID"));
	    if(WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
	       dto.setBeginStr(request.getParameter("txtCreateStartDate"));
	    if(WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
	       dto.setEndStr(request.getParameter("txtCreateEndDate"));
	    if(WebUtil.StringHaveContent(request.getParameter("txtComments")))
	       dto.setSpareStr2(request.getParameter("txtComments"));
	    return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_EXPORTCUSTOMER_DETAIL);
	}



}
