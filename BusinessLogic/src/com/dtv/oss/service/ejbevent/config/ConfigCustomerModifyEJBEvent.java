package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.dto.CsiActionReasonDetailDTO;
import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ConfigCustomerModifyEJBEvent extends AbstractEJBEvent {
	public static final int CONFIG_CUSTOMER_DELETE = Integer.MIN_VALUE + 2;

	public static final int CONFIG_CUSTOMER_UPDATE = Integer.MIN_VALUE + 1;

	public static final int CONFIG_CUSTOMER_NEW = Integer.MAX_VALUE - 1;
	
	
	
	

	private CommonSettingDataDTO dto = null;
	
	private CsiActionReasonSettingDTO csiReasonDto = null;
	
	private CsiActionReasonDetailDTO csiReasonDetailDto = null;

	private String  defaultKey ; 

	public ConfigCustomerModifyEJBEvent(CommonSettingDataDTO dto,int actionType) {
		this.dto = dto;
		this.actionType=actionType; 
		 
	}
	

	public CsiActionReasonDetailDTO getCsiReasonDetailDto() {
		return csiReasonDetailDto;
	}


	public void setCsiReasonDetailDto(CsiActionReasonDetailDTO csiReasonDetailDto) {
		this.csiReasonDetailDto = csiReasonDetailDto;
	}


	public ConfigCustomerModifyEJBEvent() {
		// TODO Auto-generated constructor stub
	}


	public CsiActionReasonSettingDTO getCsiReasonDto() {
		return csiReasonDto;
	}


	public void setCsiReasonDto(CsiActionReasonSettingDTO csiReasonDto) {
		this.csiReasonDto = csiReasonDto;
	}


	public CommonSettingDataDTO getDto() {
		return dto;
	}

	public void setDto(CommonSettingDataDTO dto) {
		this.dto = dto;
	}

	public String getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

 
}
