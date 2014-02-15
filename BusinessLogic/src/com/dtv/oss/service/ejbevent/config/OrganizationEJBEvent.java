/*
 * Created on 2006-5-9 by Stone Liang
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.config;

import java.util.List;

import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class OrganizationEJBEvent extends AbstractEJBEvent {
	public static final int ORGANIZATION_ADD   = 5211;
	public static final int ORGANIZATION_UPDATE   = 5212;
	public static final int ORGANIZATION_DELETE   = 5213;	

	private List currentDataList=null;
	private OrganizationDTO dto=null;
	
	public OrganizationEJBEvent() {
		super();
	}
	public OrganizationEJBEvent(int actionType,List currentDataList,OrganizationDTO dto) {
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
	public OrganizationDTO getDto() {
		return dto;
	}
	/**
	 * @param dto The dto to set.
	 */
	public void setDto(OrganizationDTO dto) {
		this.dto = dto;
	}
}