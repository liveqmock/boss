package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

 
import com.dtv.oss.dto.CAProductDefDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.CaEJBEvent;
 
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * CA接口信息查询
 */

public class CaProductWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CAProductDefDTO dto = new CAProductDefDTO();
		CaEJBEvent event = new CaEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志" + aType);

		// 根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CA_PRODUCTDEF_MODIFY".equalsIgnoreCase(aType)) {
			event = new CaEJBEvent(dto, CaEJBEvent.CA_PRODUCT_MODIFY);
		} else if ("CA_PRODUCTDEF_DELETE".equalsIgnoreCase(aType)) {
	       event = new CaEJBEvent(dto, CaEJBEvent.CA_PRODUCT_DELETE);
		}  else if ("CA_PRODUCTDEF_CREATE".equalsIgnoreCase(aType)) {
		      event = new CaEJBEvent(dto, CaEJBEvent.CA_PRODUCT_CREATE);
		}
		else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}

		 
			dto.setHostID(WebUtil.StringToInt(request.getParameter("txtHostID")));

		 
			dto.setOpiID(WebUtil.StringToInt(request.getParameter("txtOPI_ID")));
		 
			dto.setCaProductID(request.getParameter("txtCAProductID"));
		 
			dto.setProductName(request.getParameter("txtCaProductName"));

		 

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品设置 dto"
				+ dto.toString());

		return event;
	}

}
