package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryPalletEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * 货架查询
 * @author 260327h
 *
 */
public class QueryPalletWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		PalletDTO dto = new PalletDTO();
		
		//txtOPDepotID为修改ID标志,query页没有
		if (WebUtil.StringHaveContent(request.getParameter("txtOPPalletID"))) {
			dto.setPalletID(WebUtil.StringToInt(request
					.getParameter("txtOPPalletID")));
		} else {
			if (WebUtil.StringHaveContent(request.getParameter("txtPalletID")))
				dto.setPalletID(WebUtil.StringToInt(request
						.getParameter("txtPalletID")));
			if (WebUtil.StringHaveContent(request.getParameter("txtPalletName")))
				dto.setPalletName(request.getParameter("txtPalletName"));
			if (WebUtil.StringHaveContent(request
					.getParameter("txtPalletStatus")))
				dto.setStatus(request.getParameter("txtPalletStatus"));
			if (WebUtil.StringHaveContent(request.getParameter("txtOwner")))
				dto.setDepotID(WebUtil.StringToInt(request
						.getParameter("txtOwner")));
		}

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "查询货架 dto"
				+ dto.toString());
		return new QueryPalletEJBEvent(dto, pageFrom, pageTo,
				QueryPalletEJBEvent.QUERY_TYPE_LIST_QUERY);
	}
}
