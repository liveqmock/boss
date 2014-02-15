package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.RoleOrganizationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigRoleOrganizationEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class RoleOrganizationWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		RoleOrganizationDTO roleOrgDto =new RoleOrganizationDTO();
		 
			roleOrgDto.setId(WebUtil.StringToInt(request.getParameter("txtId")));
		 
			roleOrgDto.setDistrictId(WebUtil.StringToInt(request.getParameter("txtDistrictId")));
		 
			roleOrgDto.setServiceOrgId(WebUtil.StringToInt(request.getParameter("txtServiceOrgId")));
			
			roleOrgDto.setDiagnosisResult(request.getParameter("txtDiagnosisResult"));
			
			roleOrgDto.setSaProductId(WebUtil.StringToInt(request.getParameter("txtSaProductId")));
			
			roleOrgDto.setTroubleSubType(request.getParameter("txtTroubleSubType"));
			
			roleOrgDto.setTroubleType(request.getParameter("txtTroubleType"));
	 
			roleOrgDto.setOrgRole(request.getParameter("txtOrgRole"));
	 
			 
		 
			roleOrgDto.setIsFirst(request.getParameter("txtIsFirst"));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtCreate")))
			roleOrgDto.setDtCreate(WebUtil.StringToTimestamp(request.getParameter("txtDtCreate")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			roleOrgDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		
		 
		ConfigRoleOrganizationEJBEvent event= new ConfigRoleOrganizationEJBEvent();
		if("CREATE".equalsIgnoreCase(request.getParameter("Action")))
			event.setActionType(ConfigRoleOrganizationEJBEvent.CREATE_ROLE_ORGANIZATION);
		else if("MODIFY".equalsIgnoreCase(request.getParameter("Action")))
			event.setActionType(ConfigRoleOrganizationEJBEvent.MODIFY_ROLE_ORGANIZATION);
		else if("DELETE".equalsIgnoreCase(request.getParameter("Action")))
			event.setActionType(ConfigRoleOrganizationEJBEvent.DELETE_ROLE_ORGANIZATION);
		else{
			LogUtility.log(getClass(),LogLevel.WARN,"RoleOrganizationWebAction，没有发现动作类型!");
			return null;
		}

		event.setRoleOrgDto(roleOrgDto);
		
		return event;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
