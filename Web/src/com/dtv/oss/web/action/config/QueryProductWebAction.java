package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryProductWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ProductDTO dto =new ProductDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDateFrom")))
			dto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDateTo")))
			dto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
		if(WebUtil.StringHaveContent(request.getParameter("txtNewSAFlag")))
			dto.setNewsaFlag(request.getParameter("txtNewSAFlag"));
		if(WebUtil.StringHaveContent(request.getParameter("txtProductClass")))
			dto.setProductClass(request.getParameter("txtProductClass"));
		if(WebUtil.StringHaveContent(request.getParameter("txtProductID")))
			dto.setProductID(WebUtil.StringToInt(request.getParameter("txtProductID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtProductName")))
			dto.setProductName(request.getParameter("txtProductName"));
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CONFIG_PRODUCT_BASE_INFO_QUERY);	
	}
    
}
