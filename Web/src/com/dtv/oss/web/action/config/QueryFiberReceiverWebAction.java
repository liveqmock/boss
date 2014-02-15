package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FiberReceiverDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * ≤÷ø‚≤È—Ø
 * @author 260327h
 *
 */
public class QueryFiberReceiverWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		FiberReceiverDTO dto = new FiberReceiverDTO();
		
		 
		if (WebUtil.StringHaveContent(request.getParameter("txtFiberReceiverCode")))  
			dto.setFiberReceiverCode(request
					.getParameter("txtFiberReceiverCode"));
		if (WebUtil.StringHaveContent(request.getParameter("txtID")))  
			dto.setId(WebUtil.StringToInt(request
					.getParameter("txtID")));
		 
		if (WebUtil.StringHaveContent(request.getParameter("txtFiberTransmitterId")))
				dto.setFiberTransmitterId (WebUtil.StringToInt(request
						.getParameter("txtFiberTransmitterId")));
			 
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setDistrictId(WebUtil.StringToInt(request
					.getParameter("txtDistrictID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
			dto.setDetailAddress(request
					.getParameter("txtDetailAddress"));
	 
		return new QueryServiceResourceEJBEvent(dto, pageFrom, pageTo,
				QueryServiceResourceEJBEvent.QUERY_FIBER_RECEIVER);
	}
}
