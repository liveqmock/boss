package com.dtv.oss.service.ejbevent.csr;

import java.util.Collection;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.CustomerComplainDTO;
import com.dtv.oss.dto.wrap.customer.ComplainWrap;

/**
 * @author 260904l
 */
public class CustomerComplainEJBEvent extends CsrAbstractEJbevent {
	public static final int COMPLAIN_CREATE_PRE = 1;
	public static final int COMPLAIN_PROCESS_SUCCESS = 2;
	public static final int COMPLAIN_PROCESS_FAILURE = 3;
	public static final int COMPLAIN_TRANSFER = 11;
	public static final int COMPLAIN_MANUALEND = 12;
	public static final int COMPLAIN_CALLBACK = 13;
	public static final int COMPLAIN_CREATE_NOPRE=14;
	
	private CustomerComplainDTO dto;
	private CustComplainProcessDTO pdto;
	private int eventType;
	private Collection  callBackInfoList;
	
	
	public CustomerComplainEJBEvent(){
		super();
	}

	public CustomerComplainEJBEvent(ComplainWrap wrap,int eventType){
		this.dto=wrap.getDto();
		this.pdto=wrap.getPdto();
		this.callBackInfoList=wrap.getCallBackInfoList();
		this.eventType=eventType;
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

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Collection getCallBackInfoList() {
		return callBackInfoList;
	}

	public void setCallBackInfoList(Collection callBackInfoList) {
		this.callBackInfoList = callBackInfoList;
	}

	
}
