package com.dtv.oss.web.action.config;


public class QueryConfigCustomerNationalityWebAction extends
		QueryConfigCustomerWebAction {

	protected String getDTONameValue() {

		return ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_NATIONALITY;
	}
	protected String getPageTitle() {
		return "客户国籍列表";
	}
}
