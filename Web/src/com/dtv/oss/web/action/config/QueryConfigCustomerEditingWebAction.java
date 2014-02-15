package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigCustomerQueryEJBEvent;

public class QueryConfigCustomerEditingWebAction extends
		QueryConfigCustomerWebAction {

	private String name = null;

	public void doStart(HttpServletRequest request) {
		name = request
				.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_NAME);
		super.doStart(request);
		String queryURL = getQueryURL(name);
		if (queryURL != null && queryURL.length() > 0) {
			request
					.setAttribute(
							ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_ATTRIBUTE_BACK_URL,
							queryURL);
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CommonSettingDataDTO dto = new CommonSettingDataDTO();

		name = request
				.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_NAME);
		dto.setName(name);

		EJBEvent event = null;

		String key = request
				.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_KEY);
		dto.setKey(key);
		event = new ConfigCustomerQueryEJBEvent(
				dto,
				ConfigCustomerQueryEJBEvent.CONFIG_CUSTOMER_QUERY_DETAIL_MODIFIING);

		return event;
	}

	protected String getDTONameValue() {

		return "";
	}

	protected String getPageTitle() {
		System.out.println("name:" + name);
		String title = "修改";
		if (name != null && name.length() > 0) {
			if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TYPE)) {
				title = title + "客户类型";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TITLE)) {
				title = title + "客户称呼";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_NATIONALITY)) {
				title = title + "国籍";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_CARDTYPE)) {
				title = title + "证件类型";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CURRENCY_TYPE)) {
				title = title + "货币类型";
			}
			else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_SOURCE_TYPE)) {
				title = title + "来源渠道";
			}
		}

		return title;
	}

	private String getQueryURL(String name) {
		String url = "oss//customer_service_first_enter.screen";
		if (name != null && name.length() > 0) {
			if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TYPE)) {
				url = "config_customer_type.do";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TITLE)) {
				url = "config_customer_title.do";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_NATIONALITY)) {
				url = "config_customer_nationality.do";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_CARDTYPE)) {
				url = "config_customer_cardtype.do";
			} else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CURRENCY_TYPE)) {
				url = "config_currency_type.do";
			}
			else if (name
					.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_SOURCE_TYPE)) {
				url = "config_customer_sourceType.do";
			}
		}
		return url;
	}

}
