package com.dtv.oss.web.action.system;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;

public class QueryHintWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		BillBoardDTO dto=new BillBoardDTO();
		return new SystemQueryEJBEvent(dto,pageFrom,pageTo,SystemQueryEJBEvent.QUERY_BILLBOARD);
	}
}
