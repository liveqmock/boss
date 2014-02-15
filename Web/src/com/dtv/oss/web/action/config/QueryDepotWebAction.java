package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryDepotEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * 仓库查询
 * @author 260327h
 *
 */
public class QueryDepotWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		DepotDTO dto = new DepotDTO();
		
		//txtOPDepotID为修改ID标志,query页没有
		if (WebUtil.StringHaveContent(request.getParameter("txtOPDepotID"))) {
			dto.setDepotID(WebUtil.StringToInt(request
					.getParameter("txtOPDepotID")));
		} else {
			if (WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
				dto.setDepotID(WebUtil.StringToInt(request
						.getParameter("txtDepotID")));
			if (WebUtil.StringHaveContent(request.getParameter("txtDepotName")))
				dto.setDepotName(request.getParameter("txtDepotName"));
			if (WebUtil.StringHaveContent(request
					.getParameter("txtDepotStatus")))
				dto.setStatus(request.getParameter("txtDepotStatus"));
			if (WebUtil.StringHaveContent(request.getParameter("txtOwner")))
				dto.setOwnerID(WebUtil.StringToInt(request
						.getParameter("txtOwner")));
		}

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "查询仓库 dto"
				+ dto.toString());
		return new QueryDepotEJBEvent(dto, pageFrom, pageTo,
				QueryDepotEJBEvent.QUERY_TYPE_LIST_QUERY);
	}
}
