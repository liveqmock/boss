package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.VOIPEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class VOIPProductWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPProductDTO dto=new VOIPProductDTO();
		String aType = null;
		VOIPEJBEvent event=new VOIPEJBEvent(); 
		if (WebUtil.StringHaveContent(request.getParameter("actionFlag")))
			aType = request.getParameter("actionFlag");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "²Ù×÷±êÖ¾" + aType);
		dto.setDescription(request.getParameter("txtdescription"));
		dto.setProductID(WebUtil.StringToInt(request.getParameter("txtSMSName")));
		dto.setPropertyName(request.getParameter("txtvoipProductName"));
		dto.setSssrvCode(request.getParameter("txtserviceCode"));
		dto.setSssrvType(request.getParameter("txtserviceType"));
		if("add".equals(aType)){
			
			event.setActionType(VOIPEJBEvent.PRODUCT_ADD);
			 
		}else if("modify".equals(aType)){
			event.setActionType(VOIPEJBEvent.PRODUCT_MODIFY);
			dto.setDtLastmod(request.getParameter("txtDtLastmod"));
		}
		event.setDto(dto);
		return event;
	}
}
