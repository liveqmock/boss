package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class ModifyOpGroupWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		OpGroupDTO dto=new OpGroupDTO();
		
		 
			dto.setOpGroupName(request.getParameter("txtOpGroupName"));
		 
			dto.setOpGroupLevel(request.getParameter("txtOpGroupLevel"));
		 
		   dto.setOpGroupDesc(request.getParameter("txtDescription"));
		   dto.setSystemFlag(request.getParameter("txtSystemFlag"));
		if(WebUtil.StringHaveContent(request.getParameter("txtOpGroupID")))
			dto.setOpGroupID(WebUtil.StringToInt(request.getParameter("txtOpGroupID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		 
		ConfigSystemEJBEvent event= new ConfigSystemEJBEvent();
		if("CREATE".equalsIgnoreCase(request.getParameter("Action")))
			event.setActionType(EJBEvent.ADD_OPGROUP);
		else if("MODIFY".equalsIgnoreCase(request.getParameter("Action")))
			event.setActionType(EJBEvent.MODIFY_OPGROUP);
		else{
			//throw new WebActionException("产品管理操作类型未知！");
			LogUtility.log(getClass(),LogLevel.WARN,"ModifyOpGroupWebAction，没有发现动作类型!");
			return null;
		}

		event.setOpGroupDto(dto);
		
		return event;
	}

	 

}
