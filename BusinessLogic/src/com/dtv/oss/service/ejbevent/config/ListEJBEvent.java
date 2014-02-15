/*
 * Created on 2006-5-9 by Stone Liang
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.config;

import java.util.List;

import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ListEJBEvent extends AbstractEJBEvent {
	public static final int DISTRICT_SETTING_ADD   = 5211;
	public static final int DISTRICT_SETTING_UPDATE   = 5212;
	public static final int DISTRICT_SETTING_DELETE   = 5213;	

	private List currentDataList=null;
	private DistrictSettingDTO dto=null;
	
	public ListEJBEvent() {
		super();
	}
	public ListEJBEvent(int actionType,List currentDataList,DistrictSettingDTO dto) {
		this.actionType=actionType;
		this.currentDataList=currentDataList;
		this.dto=dto;
	}
	public List getCurrentDataList() {
		return this.currentDataList;
	}
	/**
	 * @return Returns the dto.
	 */
	public DistrictSettingDTO getDto() {
		return dto;
	}
	/**
	 * @param dto The dto to set.
	 */
	public void setDto(DistrictSettingDTO dto) {
		this.dto = dto;
	}
}