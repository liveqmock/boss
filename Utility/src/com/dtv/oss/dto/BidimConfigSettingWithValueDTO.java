package com.dtv.oss.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BidimConfigSettingWithValueDTO extends BidimConfigSettingDTO {
	private BidimConfigSettingDTO bidimConfigSettingDTO;

	private List valueList = new ArrayList();

	public BidimConfigSettingWithValueDTO() {
	}

	public BidimConfigSettingWithValueDTO(
			BidimConfigSettingDTO bidimConfigSettingDTO) {
		this.bidimConfigSettingDTO = bidimConfigSettingDTO;
	}

	public void addOneValue(BidimConfigSettingValueDTO valueDTO) {
		valueList.add(valueDTO);
	}

	public void addValues(List valueDTOsList) {
		valueList.addAll(valueDTOsList);
	}

	public boolean removeOneValue(BidimConfigSettingWithValueDTO valueDTO) {

		return valueList.remove(valueDTO);
	}

	public ArrayList getValueList() {
		return new ArrayList(valueList);
	}

	public void setBidimConfigSettingDTO(BidimConfigSettingDTO dto) {
		if (dto == null) {
			throw new IllegalArgumentException(
					"the BidimConfigSettingDTO type argument dto can't be null!");
		}
		bidimConfigSettingDTO = dto;
	}

	public BidimConfigSettingDTO getBidimConfigSettingDTO() {
		if (bidimConfigSettingDTO == null) {
			bidimConfigSettingDTO = new BidimConfigSettingDTO();
		}
		return bidimConfigSettingDTO;
	}

	public int getId() {
		return getBidimConfigSettingDTO().getId();
	}

	public void setId(int id) {
		getBidimConfigSettingDTO().setId(id);
	}

	public String getName() {
		return getBidimConfigSettingDTO().getName();
	}

	public void setName(String name) {
		getBidimConfigSettingDTO().setName(name);
	}

	public String getDescription() {
		return getBidimConfigSettingDTO().getDescription();
	}

	public void setDescription(String description) {
		getBidimConfigSettingDTO().setDescription(description);
	}

	public String getConfigType() {
		return getBidimConfigSettingDTO().getConfigType();
	}

	public void setConfigType(String configType) {
		getBidimConfigSettingDTO().setConfigType(configType);
	}

	public String getConfigSubType() {
		return getBidimConfigSettingDTO().getConfigSubType();
	}

	public void setConfigSubType(String configSubType) {
		getBidimConfigSettingDTO().setConfigSubType(configSubType);
	}

	public int getServiceId() {
		return getBidimConfigSettingDTO().getServiceId();
	}

	public void setServiceId(int serviceId) {
		getBidimConfigSettingDTO().setServiceId(serviceId);
	}

	public String getValueType() {
		return getBidimConfigSettingDTO().getValueType();
	}

	public void setValueType(String valueType) {
		getBidimConfigSettingDTO().setValueType(valueType);
	}

	public String getAllowNull() {
		return getBidimConfigSettingDTO().getAllowNull();
	}

	public void setAllowNull(String allowNull) {
		getBidimConfigSettingDTO().setAllowNull(allowNull);
	}

	public String getStatus() {
		return getBidimConfigSettingDTO().getStatus();
	}

	public void setStatus(String status) {
		getBidimConfigSettingDTO().setStatus(status);
	}

	public Timestamp getDtCreate() {
		return getBidimConfigSettingDTO().getDtCreate();
	}

	public void setDtCreate(Timestamp dtCreate) {
		getBidimConfigSettingDTO().setDtCreate(dtCreate);
	}

	public Timestamp getDtLastmod() {
		return getBidimConfigSettingDTO().getDtLastmod();
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		getBidimConfigSettingDTO().setDtLastmod(dtLastmod);
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		return getBidimConfigSettingDTO().equals(obj);
		/*
		 * if (getBidimConfigSettingDTO().equals(obj)) { if (obj instanceof
		 * BidimConfigSettingWithValueDTO) { List thisValueList =
		 * getValueList(); List objValueList = ((BidimConfigSettingWithValueDTO)
		 * obj) .getValueList(); if (thisValueList.size() !=
		 * objValueList.size()) { return false; } // compare values sreturn
		 * true; } }
		 * 
		 * return false;
		 */
	}

	public int hashCode() {
		return getBidimConfigSettingDTO().hashCode();
	}

	public String toString() {

		return getBidimConfigSettingDTO().toString();
	}

	public void put(String field) {
		getBidimConfigSettingDTO().put(field);
	}

	public java.util.Map getMap() {
		return getBidimConfigSettingDTO().getMap();
	}
}
