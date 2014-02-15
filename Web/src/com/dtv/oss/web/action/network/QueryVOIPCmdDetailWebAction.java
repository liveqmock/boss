package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPCmdProDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPEventCmdEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * VOIP接口命令历史记录详细查询
 * @author 260904l
 *
 */
public class QueryVOIPCmdDetailWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPCmdProDTO dto=new VOIPCmdProDTO(); 
		String seqNo=request.getParameter("sID");
		if(WebUtil.StringHaveContent(seqNo)){
			dto.setSeqNo(WebUtil.StringToInt(seqNo));
		}
		return new QueryVOIPEventCmdEvent(dto,pageFrom,pageTo,QueryVOIPEventCmdEvent.QUERY_VOIP_CMDLOG);
	}
}
