package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BossConfigurationDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-5-26
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyBossConfigWebAction extends GeneralWebAction {

	 
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	BossConfigurationDTO dto = new BossConfigurationDTO();
    	ConfigSystemEJBEvent event= new ConfigSystemEJBEvent();
       
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.BOSS_CONFIG_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.BOSS_CONFIG_MODIFY);
	    }
	      
	    
	        dto.setSoftwareName(request.getParameter("txtSoftWareName"));
	    
    	     dto.setSoftwarEversion(request.getParameter("txtSoftWareVersion"));
	   
	    	   dto.setDatabaseVersion(request.getParameter("txtDatabaseVersion"));
	     
    	 
            dto.setDeveloper(request.getParameter("txtDeveloper"));
    	
     
            dto.setMsoSystemName(request.getParameter("txtMsoSystemName"));
     
            dto.setMsoName(request.getParameter("txtMsoName")); 
    	 
            dto.setLicensedMaxUser(WebUtil.StringToInt(request.getParameter("txtLicensedMaxUser"))); 
     
            dto.setLicenseValidFrom(request.getParameter("txtLicenseValidFrom")); 
    	 
            dto.setLicenseValidTo(request.getParameter("txtLicenseValidTo")); 
     
            dto.setInstallTime(WebUtil.StringToTimestamp(request.getParameter("txtInstallTime"))); 
    	 
           event.setBcDto(dto);
        
        return event;
    }
}
