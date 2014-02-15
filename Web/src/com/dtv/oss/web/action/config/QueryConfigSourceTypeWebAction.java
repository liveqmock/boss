package com.dtv.oss.web.action.config;

public class QueryConfigSourceTypeWebAction extends
		QueryConfigCustomerWebAction {

	protected String getDTONameValue() {

		return ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_SOURCE_TYPE;
	}
	protected String getPageTitle() {
		return "来源渠道列表";
	}
}
