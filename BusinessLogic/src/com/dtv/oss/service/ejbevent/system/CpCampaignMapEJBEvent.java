package com.dtv.oss.service.ejbevent.system;

import com.dtv.oss.dto.CPCampaignMapDTO;

public class CpCampaignMapEJBEvent extends SystemEJBEvent{
	
	private int actionType;
	private CPCampaignMapDTO cpCampaignMapDTO;	
	private int customerID;
	
	public CPCampaignMapDTO getCpCampaignMapDTO() {
		return cpCampaignMapDTO;
	}

	public void setCpCampaignMapDTO(CPCampaignMapDTO cpCampaignMapDTO) {
		this.cpCampaignMapDTO = cpCampaignMapDTO;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
}