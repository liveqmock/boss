/*
 * Created on 2006-1-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.config;

import java.util.List;

import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.dto.BidimConfigSettingValueDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author Chen jiang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConfigBidimEJBEvent extends AbstractEJBEvent {

	private BidimConfigSettingDTO bidimDto;

	private BidimConfigSettingValueDTO bidimValueDto;

	private List toBeDeletedBidimValuesList = null;

	private List newAppendedBidimValuesList = null;

	public ConfigBidimEJBEvent() {

	}

	/**
	 * @return Returns the bidimDto.
	 */
	public BidimConfigSettingDTO getBidimDto() {
		return bidimDto;
	}

	/**
	 * @param bidimDto
	 *            The bidimDto to set.
	 */
	public void setBidimDto(BidimConfigSettingDTO bidimDto) {
		this.bidimDto = bidimDto;
	}

	/**
	 * @return Returns the bidimValueDto.
	 */
	public BidimConfigSettingValueDTO getBidimValueDto() {
		return bidimValueDto;
	}

	/**
	 * @param bidimValueDto
	 *            The bidimValueDto to set.
	 */
	public void setBidimValueDto(BidimConfigSettingValueDTO bidimValueDto) {
		this.bidimValueDto = bidimValueDto;
	}

	public List getNewAppendedBidimValuesList() {
		return newAppendedBidimValuesList;
	}

	public void setNewAppendedBidimValuesList(List newAppendedBidimValuesList) {
		this.newAppendedBidimValuesList = newAppendedBidimValuesList;
	}

	public List getToBeDeletedBidimValuesList() {
		return toBeDeletedBidimValuesList;
	}

	public void setToBeDeletedBidimValuesList(List toBeDeletedBidimValuesList) {
		this.toBeDeletedBidimValuesList = toBeDeletedBidimValuesList;
	}

}
