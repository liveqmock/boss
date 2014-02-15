package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.SystemSettingDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
/*
 * 修改设备管理全局配置
 */
public class SystemSettingWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		SystemSettingDTO dto = new SystemSettingDTO();
		ConfigSystemEJBEvent event = new ConfigSystemEJBEvent();
        dto.setName(request.getParameter("txtName")); 
		dto.setValue(request.getParameter("txtValue"));
		dto.setStatus(request.getParameter("txtStatus"));
		dto.setValueType(request.getParameter("txtValueType"));
		dto.setDescription(request.getParameter("txtDescription"));
		event.setActionType(EJBEvent.SYSTEM_SETTING_MODIFY); 
		event.setSysSettingDto(dto); 
          return event;
	}

}
