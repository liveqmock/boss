package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigCustomerQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;

public abstract class QueryConfigCustomerWebAction extends QueryWebAction {

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		String title = getPageTitle();
		request.setAttribute("title", title);

		String name = getDTONameValue();
		if (name != null && name.length() > 0) {
			request
					.setAttribute(
							ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_NAME,
							name);
		}

	}
    
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CommonSettingDataDTO dto = new CommonSettingDataDTO();

		String name = getDTONameValue();
		if (name == null || (name = name.trim()).length() == 0) {
			name = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_NAME);
		}
		dto.setName(name);

		EJBEvent event = null;
		event = new ConfigCustomerQueryEJBEvent(dto,
				ConfigCustomerQueryEJBEvent.CONFIG_CUSTOMER_QUERY_LIST);

		return event;
	}

	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		Object payload = cmdResponse.getPayload();
		LogUtility.log(getClass(), LogLevel.DEBUG, "payload" + payload);
		Object obj = request.getAttribute("ResponseQueryResult");
		LogUtility.log(getClass(), LogLevel.DEBUG, "ResponseQueryResult" + obj);
		if (obj == null) {
			// request.setAttribute("ResponseQueryResult", payload);
		}

	}

	abstract protected String getDTONameValue();

	abstract protected String getPageTitle();

	// abstract protected String getQueryURL();

}
