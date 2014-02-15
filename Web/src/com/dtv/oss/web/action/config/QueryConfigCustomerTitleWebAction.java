package com.dtv.oss.web.action.config;


public class QueryConfigCustomerTitleWebAction extends QueryConfigCustomerWebAction {

	protected String getDTONameValue() {
		
		return ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TITLE;
	}
	protected String getPageTitle() {
		return "客户称呼列表";
	}
}
