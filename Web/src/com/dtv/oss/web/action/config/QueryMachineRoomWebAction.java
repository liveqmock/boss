package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FiberNodeDTO;
import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * 光节点查询
 * @author Chen Jiang
 *
 */
public class QueryMachineRoomWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		MachineRoomDTO dto = new MachineRoomDTO();
		
		 
		if (WebUtil.StringHaveContent(request.getParameter("txtMachineRoomCode")))  
			dto.setMachineRoomCode(request
					.getParameter("txtMachineRoomCode"));
		if (WebUtil.StringHaveContent(request.getParameter("txtID")))  
			dto.setId(WebUtil.StringToInt(request
					.getParameter("txtID")));
		 
		if (WebUtil.StringHaveContent(request.getParameter("txtMachineRoomName")))
				dto.setMachineRoomName(request
						.getParameter("txtMachineRoomName"));
			 
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setDistrictId(WebUtil.StringToInt(request
					.getParameter("txtDistrictID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
			dto.setDetailAddress(request
					.getParameter("txtDetailAddress"));
	 
		return new QueryServiceResourceEJBEvent(dto, pageFrom, pageTo,
				QueryServiceResourceEJBEvent.QUERY_MACHINE_ROOM);
	}
}
