package com.dtv.oss.dto.wrap.customer;

import java.util.Collection;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.CustomerComplainDTO;

public class ComplainWrap implements java.io.Serializable{
	private CustomerComplainDTO dto;
	private CustComplainProcessDTO pdto; 
	private Collection  callBackInfoList;
	
	public ComplainWrap(){
		this.dto=new CustomerComplainDTO();
		this.pdto=new CustComplainProcessDTO();
	}
	public ComplainWrap(CustomerComplainDTO dto,CustComplainProcessDTO pdto){
		this.dto=dto;
		this.pdto=pdto;
	}
	public CustomerComplainDTO getDto() {
		return dto;
	}
	public void setDto(CustomerComplainDTO dto) {
		this.dto = dto;
	}
	public CustComplainProcessDTO getPdto() {
		return pdto;
	}
	public void setPdto(CustComplainProcessDTO pdto) {
		this.pdto = pdto;
	}
	public Collection getCallBackInfoList() {
		return callBackInfoList;
	}
	public void setCallBackInfoList(Collection callBackInfoList) {
		this.callBackInfoList = callBackInfoList;
	}
}
