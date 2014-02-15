package com.dtv.oss.web.action.config;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigCustomerModifyEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ModifyConfigCustomerWebAction extends GeneralWebAction {

	private String name = null;

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		name = request
				.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_NAME);
		String key = request
				.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_KEY);
		String actionType = request
				.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_MODIFY_TYPE);

		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in execute method encapsulateData execute..."
						+ "name,key,actionType" + name + "," + key + ","
						+ actionType);
    
		CommonSettingDataDTO dto = new CommonSettingDataDTO();
		//判断是否超出字节
		byte[] keybt=key.getBytes();
		if(keybt.length>5)
		  throw new WebActionException("键的输入长度太长！");
		dto.setName(name);
		dto.setKey(key);

		EJBEvent event = null;
		if (actionType == null) {
			actionType = "";
		} else {
			actionType = actionType.trim();
		}
		if (actionType.equals(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_MODIFY_TYPE_NEW)) {
			String value = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_VALUE);
			String status = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_STATUS);
			String desc = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_DESCRIPTION);
			
			String defaultOrNot = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_DEFAULTORNOT);
			String priority = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_PRIORITY);
			String grade = request
			.getParameter("operaterLevel");
			dto.setValue(value);
			dto.setStatus(status);
			dto.setDescription(desc);
			dto.setDefaultOrNot(defaultOrNot);
			dto.setPriority(WebUtil.StringToInt(priority));
			dto.setValue(value);
			dto.setGrade(WebUtil.StringToInt(grade));
			
			ConfigCustomerModifyEJBEvent Cevent = new ConfigCustomerModifyEJBEvent(dto,
					ConfigCustomerModifyEJBEvent.CONFIG_CUSTOMER_NEW);
			if (WebUtil.StringHaveContent(request.getParameter("defaultKey"))){
				Cevent.setDefaultKey(request.getParameter("defaultKey"));
			}
			
			event=Cevent;
			LogUtility
					.log(getClass(), LogLevel.DEBUG,
							"in execute method encapsulateData execute..."
									+ " name,key,actionType,value,desc: "
									+ name + "," + key + "," + actionType + ","
									+ value + "," + desc);
		} else if (actionType
				.equals(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_MODIFY_TYPE_UPDATE)) {
			String value = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_VALUE);
			String status = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_STATUS);
			String desc = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_DESCRIPTION);
			String defaultOrNot = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_DEFAULTORNOT);
			String priority = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_PRIORITY);
			String dtLastMod = request.getParameter("dt_lastmod");
			String grade = request
			.getParameter("operaterLevel");
			if (dtLastMod == null
					|| (dtLastMod = dtLastMod.trim()).length() == 0) {
				dtLastMod = "0";
			}

			dto.setKey(key);
			dto.setValue(value);
			dto.setStatus(status);
			dto.setDescription(desc);
			dto.setDefaultOrNot(defaultOrNot);
			dto.setPriority(WebUtil.StringToInt(priority));
			dto.setDtLastmod(new Timestamp(Long.valueOf(dtLastMod).longValue()));
			dto.setGrade(WebUtil.StringToInt(grade));
			ConfigCustomerModifyEJBEvent Cevent = new ConfigCustomerModifyEJBEvent(dto,ConfigCustomerModifyEJBEvent.CONFIG_CUSTOMER_UPDATE);
			if (WebUtil.StringHaveContent(request.getParameter("defaultKey"))){
				Cevent.setDefaultKey(request.getParameter("defaultKey"));
			}
			event=Cevent;
			 
			LogUtility
					.log(getClass(), LogLevel.DEBUG,
							"in execute method encapsulateData execute..."
									+ " name,key,actionType,value,desc: "
									+ name + "," + key + "," + actionType + ","
									+ value + "," + desc);
		} else if (actionType
				.equals(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_MODIFY_TYPE_DELETE)) {
			event = new ConfigCustomerModifyEJBEvent(dto,
					ConfigCustomerModifyEJBEvent.CONFIG_CUSTOMER_DELETE);
		}

		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in execute method encapsulateData EJBEvent..." + event);

		return event;
	}

	public void doEnd(HttpServletRequest request, CommandResponse commandRespose) {
		super.doEnd(request, commandRespose);
		String backURL = "";
		if (name == null) {
			name = request
					.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_NAME);
		}
		if (name
				.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TYPE)) {
			backURL = "config_customer_type.do";
		} else if (name
				.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_TITLE)) {
			backURL = "config_customer_title.do";
		} else if (name
				.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_NATIONALITY)) {
			backURL = "config_customer_nationality.do";
		} else if (name
				.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CUSTOMER_CARDTYPE)) {
			backURL = "config_customer_cardtype.do";
		} else if (name
				.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_CURRENCY_TYPE)) {
			backURL = "config_currency_type.do";
		}
		else if (name
				.equals(ConfigCustomerWebActionConstants.COMMON_DATA_NAME_VALUE_SOURCE_TYPE)) {
			backURL = "config_customer_sourceType.do";
		}
		request
				.setAttribute(
						ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_ATTRIBUTE_BACK_URL,
						backURL);

		String title = "";
		String actionType = request
				.getParameter(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_PARAMETER_MODIFY_TYPE);

		if (actionType
				.equals(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_MODIFY_TYPE_NEW)) {
			title = "添加已提交";
		} else if (actionType
				.equals(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_MODIFY_TYPE_UPDATE)) {
			title = "修改已提交";
		} else if (actionType
				.equals(ConfigCustomerWebActionConstants.CONFIG_CUSTOMER_MODIFY_TYPE_DELETE)) {
			title = "删除已提交";
		}
		request.setAttribute("page_title", title);

	}
}
