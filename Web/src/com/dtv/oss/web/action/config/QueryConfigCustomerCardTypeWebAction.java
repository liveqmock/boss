package com.dtv.oss.web.action.config;

public class QueryConfigCustomerCardTypeWebAction extends
		QueryConfigCustomerWebAction {

	protected String getDTONameValue() {

		return ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_CARDTYPE;
	}
	protected String getPageTitle() {
		return "证件类型列表";
	}
}
