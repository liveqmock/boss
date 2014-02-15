package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public  class ConfigPaymentTypeQueryWebAction extends QueryWebAction {

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		String title = getPageTitle();
		request.setAttribute("title", title);

		 
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CommonSettingDataDTO dto = new CommonSettingDataDTO();

		 
		dto.setName("SET_V_CSIPAYMENTSUBTYPE");
		 if (WebUtil.StringHaveContent(request.getParameter("txtKey1")))
         dto.setKey(request.getParameter("txtKey1"));
		 EJBEvent event = null;
		 event = new ConfigQueryEJBEvent(dto,0,0,
				ConfigQueryEJBEvent.CONFIG_PAYMENT_TYPE_QUERY_LIST);

		return event;
	}

	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		Object payload = cmdResponse.getPayload();
		LogUtility.log(getClass(), LogLevel.DEBUG, "payload" + payload);
		Object obj = request.getAttribute("ResponseQueryResult");
		LogUtility.log(getClass(), LogLevel.DEBUG, "ResponseQueryResult" + obj);
		if (obj == null) {
			//request.setAttribute("ResponseQueryResult", payload);
		}

	}

	 
	  protected String getPageTitle(){

	   return "抵扣券类型列表";

}}

