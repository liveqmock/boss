package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.RoleOrganizationDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryRoleOrganizationWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		RoleOrganizationDTO roleOrgDto =new RoleOrganizationDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtId")))
			roleOrgDto.setId(WebUtil.StringToInt(request.getParameter("txtId")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictId")))
			roleOrgDto.setDistrictId(WebUtil.StringToInt(request.getParameter("txtDistrictId")));
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceOrgId")))
			roleOrgDto.setServiceOrgId(WebUtil.StringToInt(request.getParameter("txtServiceOrgId")));
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgRole")))
			roleOrgDto.setOrgRole(request.getParameter("txtOrgRole"));
		 
		if(WebUtil.StringHaveContent(request.getParameter("txtIsFirst")))
			roleOrgDto.setIsFirst(request.getParameter("txtIsFirst"));
		
		
		return new ConfigQueryEJBEvent(roleOrgDto,pageFrom,pageTo,ConfigQueryEJBEvent.ORLE_ORGANIZATION_QUERY);	
	}
    
}
