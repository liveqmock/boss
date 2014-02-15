package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ServiceAccountDeviceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryServiceAccountDeviceWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ServiceAccountDeviceDTO dto=new ServiceAccountDeviceDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDeviceClass"))){
			dto.setDeviceClass(request.getParameter("txtDeviceClass"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtSerialNoBegin"))){
			dto.setSerialNo(request.getParameter("txtSerialNoBegin"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtDeviceModel"))){
			dto.setDeviceModel(request.getParameter("txtDeviceModel"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceCode"))){
			dto.setServiceCode(request.getParameter("txtServiceCode"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))){
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))); 
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))){
			dto.setStatus(request.getParameter("txtStatus")); 
		}
		//增加产品查询条件
		if (WebUtil.StringHaveContent(request.getParameter("txtProductID"))){
			dto.setServiceID(WebUtil.StringToInt(request.getParameter("txtProductID"))); 
		}
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_SERVICEACCOUNT_TERMINALDEVICE);
	}

}
