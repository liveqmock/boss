package com.dtv.oss.web.action.config;

public class QueryConfigCustomerTypeWebAction extends
		QueryConfigCustomerWebAction {

	protected String getDTONameValue() {
		return ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TYPE;

	}

	protected String getPageTitle() {
		return "客户类型列表";
	}
}
