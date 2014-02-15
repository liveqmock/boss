package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

 
import com.dtv.oss.dto.CAProductDTO;
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

public class CaProductSMSWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CAProductDTO dto = new CAProductDTO();
		CaEJBEvent event = new CaEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志" + aType);

		// 根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CA_PRODUCTSMS_MODIFY".equalsIgnoreCase(aType)) {
			event = new CaEJBEvent(dto, CaEJBEvent.CA_PRODUCTSMS_MODIFY);
		} else if ("CA_PRODUCTSMS_DELETE".equalsIgnoreCase(aType)) {
	       event = new CaEJBEvent(dto, CaEJBEvent.CA_PRODUCTSMS_DELETE);
		}  else if ("CA_PRODUCTSMS_CREATE".equalsIgnoreCase(aType)) {
		      event = new CaEJBEvent(dto, CaEJBEvent.CA_PRODUCTSMS_CREATE);
		}
		else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request
					.getParameter("txtDtLastmod"))); 
		 dto.setProductId(WebUtil.StringToInt(request.getParameter("txtProductID")));
         dto.setOpiID(WebUtil.StringToInt(request.getParameter("txtOPI_ID")));
         dto.setConditionId(WebUtil.StringToInt(request.getParameter("txtConditionID")));
		 dto.setEntitlement(request.getParameter("txtEntitlement"));
		 dto.setDescription(request.getParameter("txtDesc"));

		 

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品设置 dto"
				+ dto.toString());

		return event;
	}

}
