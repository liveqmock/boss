/*
 * Created on 2004-8-22
 * 
 * @author Mac Wang
 */
package com.dtv.oss.service.ejbevent.csr;

import java.util.ArrayList;

import com.dtv.oss.dto.*;
import com.dtv.oss.service.ejbevent.*;

public class CpConfigedPropertyEJBEvent extends AbstractEJBEvent {
	public static final int CPCONFIGEDPROPERTY_UPDATE=100;
	public static final int CPCONFIGEDPROPERTY_DELETE=101;
	public static final int CPCONFIGEDPROPERTY_ADD=102;

	private ArrayList dtoList=new ArrayList();
	private CpConfigedPropertyDTO dto;
	private SystemEventDTO systemEventDto;

	public CpConfigedPropertyEJBEvent() {
	}

	public CpConfigedPropertyEJBEvent(CpConfigedPropertyDTO dto,
			int actionType) {
		this.dto=dto;
		this.actionType=actionType;
	}

	public CpConfigedPropertyEJBEvent(CpConfigedPropertyDTO dto,
			int actionType,ArrayList dtoList) {
		this.dto=dto;
		this.actionType=actionType;
		this.dtoList=dtoList;
	}
	/**
	 * @return Returns the dto.
	 */
	public CpConfigedPropertyDTO getDto() {
		return dto;
	}

	/**
	 * @param dto The dto to set.
	 */
	public void setDto(CpConfigedPropertyDTO dto) {
		this.dto = dto;
	}

	/**
	 * @return Returns the dtoList.
	 */
	public ArrayList getDtoList() {
		return dtoList;
	}

	/**
	 * @param dtoList The dtoList to set.
	 */
	public void setDtoList(ArrayList dtoList) {
		this.dtoList = dtoList;
	}

	/**
	 * @return Returns the systemEventDto.
	 */
	public SystemEventDTO getSystemEventDto() {
		return systemEventDto;
	}

	/**
	 * @param systemEventDto The systemEventDto to set.
	 */
	public void setSystemEventDto(SystemEventDTO systemEventDto) {
		this.systemEventDto = systemEventDto;
	}

}