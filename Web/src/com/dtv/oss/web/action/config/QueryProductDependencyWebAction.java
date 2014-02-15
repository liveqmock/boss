package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryProductDependencyWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ProductDependencyDTO dto =new ProductDependencyDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setDtCreate(WebUtil.StringToTimestamp(request.getParameter("txtCreateTime1")));
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCreateTime2")));
		if(WebUtil.StringHaveContent(request.getParameter("txtType")))
			dto.setType(request.getParameter("txtType"));
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
		
		if(WebUtil.StringHaveContent(request.getParameter("txtProductID")))
			dto.setProductId(WebUtil.StringToInt(request.getParameter("txtProductID")));
		
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CONFIG_PRODUCT_DEPENDENCY_QUERY);	
	}
    
}
