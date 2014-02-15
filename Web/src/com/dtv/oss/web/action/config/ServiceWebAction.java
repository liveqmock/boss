package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.dto.ServiceDependencyDTO;
import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.dto.ServiceResourceDetailDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.config.ConfigServiceEJBEvent;

public class ServiceWebAction extends GeneralWebAction {
	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;			
		}

	}
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException{
		ConfigServiceEJBEvent event =null;
		String actionFlag = request.getParameter("ActionFlag");
	    if (actionFlag==null){
	    	throw new WebActionException("页面传递参数错误，请与管理员联系！");	    	
	    } else if (actionFlag.equalsIgnoreCase("add_service")) {
	    	ServiceDTO  dto =new ServiceDTO();
	    	dto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
	    	dto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
	    	dto.setDescription(request.getParameter("txtDescription"));
	    	dto.setServiceName(request.getParameter("txtServiceName"));
	    	dto.setStatus(request.getParameter("txtStatus")); 
	    	event = new ConfigServiceEJBEvent(EJBEvent.SERVICE_CREATE);
	    	event.setServiceDto(dto);
	    	return event;
	    } else if (actionFlag.equalsIgnoreCase("del_service")){
	    	String txtServiceIds =request.getParameter("txtServiceIds");
		   	event =new ConfigServiceEJBEvent(EJBEvent.SERVICE_DELETE);
		   	event.setServiceIds(txtServiceIds);
		   	return event;
	    }  else if (actionFlag.equalsIgnoreCase("update_service")){
	    	ServiceDTO  dto =new ServiceDTO();
	    	dto.setServiceID(WebUtil.StringToInt(request.getParameter("txtServiceID")));
	    	dto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
	    	dto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
	    	dto.setDescription(request.getParameter("txtDescription"));
	    	dto.setServiceName(request.getParameter("txtServiceName"));
	    	dto.setStatus(request.getParameter("txtStatus")); 
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
	        event =new ConfigServiceEJBEvent(EJBEvent.SERVICE_UPDATE);
	        event.setServiceDto(dto);
	    	return event;
	    }  else if (actionFlag.equalsIgnoreCase("del_serviceDependence")){
	    	event =new ConfigServiceEJBEvent(EJBEvent.SERVICEDEPENDCE_DELETE);
	    	String txtServiceIds =request.getParameter("txtServiceID");
	    	String txtReferServiceId =request.getParameter("txtReferServiceId");
	    	event.setServiceIds(txtServiceIds);
	    	event.setReferServiceIds(txtReferServiceId);
	    	return event;
	    } else if (actionFlag.equalsIgnoreCase("add_serviceDependence")){
	    	event =new ConfigServiceEJBEvent(EJBEvent.SERVICEDEPENDCE_CREATE);
	    	ServiceDependencyDTO dto =new ServiceDependencyDTO();
	    	dto.setServiceId(WebUtil.StringToInt(request.getParameter("txtServiceID")));
	    	dto.setType(request.getParameter("txtType"));
	    	dto.setReferServiceId(WebUtil.StringToInt(request.getParameter("txtReferServiceId")));
	    	event.setServiceDependencyDto(dto);
	    	return event;
	    } else if (actionFlag.equalsIgnoreCase("add_resource")){
	    	event =new ConfigServiceEJBEvent(EJBEvent.SERVICERESOURCE_CREATE);
	    	ServiceResourceDTO dto =new ServiceResourceDTO();
	    	dto.setResourceName(request.getParameter("txtResourceName"));
	        dto.setStatus(request.getParameter("txtStatus"));
	        dto.setResourceDesc(request.getParameter("txtResourceDesc"));
	        dto.setResourceType(request.getParameter("txtResourceType"));
	        event.setServiceResourceDto(dto);
	        return event;    
	    } else if (actionFlag.equalsIgnoreCase("update_resource")){
	    	event =new ConfigServiceEJBEvent(EJBEvent.SERVICERESOURCE_UPDATE);
	    	ServiceResourceDTO dto =new ServiceResourceDTO();
	    	dto.setResourceName(request.getParameter("txtResourceName"));
	    	dto.setResourceType(request.getParameter("txtResourceType"));
	        dto.setStatus(request.getParameter("txtStatus"));
	        dto.setResourceDesc(request.getParameter("txtResourceDesc"));
	        dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
	        event.setServiceResourceDto(dto);
	        return event;	
	    } else if (actionFlag.equalsIgnoreCase("del_resource")){
	    	event =new ConfigServiceEJBEvent(EJBEvent.SERVICERESOURCE_DELETE);
	    	event.setResourceNames(request.getParameter("txtResourceNames"));
	    	return event;   
	    } else if (actionFlag.equalsIgnoreCase("add_resourceDetail")){
	    	event =new ConfigServiceEJBEvent(EJBEvent.SERVICERESOURCEDETAIL_CREATE);
	    	ServiceResourceDetailDTO dto =new ServiceResourceDetailDTO();
	    	dto.setDetailDesc(request.getParameter("txtDetailDesc"));
	    	dto.setDetailValue(request.getParameter("txtDetailValue"));
	    	dto.setResourceName(request.getParameter("txtResourceName"));
	    	dto.setStatus(request.getParameter("txtStatus"));
	    	event.setServiceResourceDetailDto(dto);
	    	return event;
	    } else if (actionFlag.equalsIgnoreCase("del_resourceDetail")){
	    	event =new ConfigServiceEJBEvent(EJBEvent.SERVICERESOURCEDETAIL_DELETE);
	    	event.setResourceDetailIds(request.getParameter("txtIds"));
	    	return event;
	    }
	    return null;
	}
}
