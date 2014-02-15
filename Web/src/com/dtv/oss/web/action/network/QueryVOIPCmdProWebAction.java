package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPCmdProDTO;
import com.dtv.oss.dto.VOIPEventCmdDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPEventCmdEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * VOIP接口命令历史记录查询
 * @author 260904l
 *
 */
public class QueryVOIPCmdProWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPCmdProDTO dto=new VOIPCmdProDTO(); 
		String queueID=request.getParameter("qID");
		if(WebUtil.StringHaveContent(queueID)){
			dto.setQueueID(WebUtil.StringToInt(queueID));
		}
		return new QueryVOIPEventCmdEvent(dto,pageFrom,pageTo,QueryVOIPEventCmdEvent.QUERY_VOIP_CMDPROCESS);
	}

}
