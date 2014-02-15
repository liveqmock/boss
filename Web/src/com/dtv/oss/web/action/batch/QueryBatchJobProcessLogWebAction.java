package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchJobEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBatchJobProcessLogWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//SpareStr1:记录ID
		if(WebUtil.StringToInt(request.getParameter("txtBatchID"))==0)
			throw new WebActionException("没有相应的记录ID，请重新查询！");
		dto.setSpareStr1(request.getParameter("txtBatchID"));

		return new QueryBatchJobEJBEvent(dto,pageFrom,pageTo,QueryBatchJobEJBEvent.QUERY_TYPE_BATCH_QUERY_LOG);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
