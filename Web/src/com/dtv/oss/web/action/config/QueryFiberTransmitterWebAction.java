package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * 光发射机查询
 * @author 260327h
 *
 */
public class QueryFiberTransmitterWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		FiberTransmitterDTO dto = new FiberTransmitterDTO();
		
		 
		if (WebUtil.StringHaveContent(request.getParameter("txtFiberTransmitterCode")))  
			dto.setFiberTransmitterCode(request
					.getParameter("txtFiberTransmitterCode"));
		if (WebUtil.StringHaveContent(request.getParameter("txtID")))  
			dto.setId(WebUtil.StringToInt(request
					.getParameter("txtID")));
		 
		if (WebUtil.StringHaveContent(request.getParameter("txtMachineRoomId")))
				dto.setMachineRoomId(WebUtil.StringToInt(request
						.getParameter("txtMachineRoomId")));
			 
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setDistrictId(WebUtil.StringToInt(request
					.getParameter("txtDistrictID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
			dto.setDetailAddress(request
					.getParameter("txtDetailAddress"));
	 
		return new QueryServiceResourceEJBEvent(dto, pageFrom, pageTo,
				QueryServiceResourceEJBEvent.QUERY_FIBER_TRANSMITTER);
	}
}
