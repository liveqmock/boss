package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPEventCmdDTO;
import com.dtv.oss.dto.wrap.VOIPEventCmdWrap;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPEventCmdEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * VOIP接口事件命令查询
 * @author 260904l
 *
 */

public class QueryVOIPEventCmdWebAction extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPEventCmdDTO dto=new VOIPEventCmdDTO(); 
		String sequenceNo=request.getParameter("sID");
		if(WebUtil.StringHaveContent(sequenceNo)){
			dto.setEventID(WebUtil.StringToInt(sequenceNo));
		}
		return new QueryVOIPEventCmdEvent(dto,pageFrom,pageTo,QueryVOIPEventCmdEvent.QUERY_VOIP_EVENTCMD);
	}
}
