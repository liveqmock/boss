package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ConfigPaymentTypeEJBEvent extends AbstractEJBEvent {
	public static final int CONFIG_PAYMENTTYPE_DELETE = Integer.MIN_VALUE + 2;

	public static final int CONFIG_PAYMENTTYPE_UPDATE = Integer.MIN_VALUE + 1;

	public static final int CONFIG_PAYMENTTYPE_NEW = Integer.MAX_VALUE - 1;

	private CommonSettingDataDTO dto = null;

	private String oldKey = null;
	
	private String defaultKey;

	private int businessType = -1;

	public ConfigPaymentTypeEJBEvent(CommonSettingDataDTO dto,
			int operatorID, String oldKey) {
		this.dto = dto;
		setOperatorID(operatorID);
		setBusinessType(operatorID);
		setOldKey(oldKey);
	}
	public ConfigPaymentTypeEJBEvent() {
		 
	}
	public CommonSettingDataDTO getDto() {
		return dto;
	}

	public void setDto(CommonSettingDataDTO dto) {
		this.dto = dto;
	}

	public String getOldKey() {
		return oldKey;
	}

	public void setOldKey(String oldKey) {
		this.oldKey = oldKey;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getDefaultKey() {
		return defaultKey;
	}
	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

}
