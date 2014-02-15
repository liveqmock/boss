package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryProductPackageWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ProductPackageDTO dto =new ProductPackageDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDateFrom")))
			dto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDateTo")))
			dto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
		 
		if(WebUtil.StringHaveContent(request.getParameter("txtPackageClassify")))
			dto.setPackageClass(request.getParameter("txtPackageClassify"));
		if(WebUtil.StringHaveContent(request.getParameter("txtPackageID")))
			dto.setPackageID(WebUtil.StringToInt(request.getParameter("txtPackageID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtName")))
			dto.setPackageName(request.getParameter("txtName"));
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		
		    dto.setDescription("config");
		
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CONFIG_PRODUCT_PACKAGE_BASE_INFO_QUERY);	
	}
    
}
