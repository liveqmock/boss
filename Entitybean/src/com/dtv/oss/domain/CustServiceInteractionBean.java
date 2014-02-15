package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;
import java.math.BigDecimal;

abstract public class CustServiceInteractionBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		// setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CustServiceInteractionDTO dto)
			throws CreateException {
		setReferSheetID(dto.getReferSheetID());
		setType(dto.getType());
		setCreateTime(dto.getCreateTime());
		setCustomerID(dto.getCustomerID());
		setAccountID(dto.getAccountID());
		setAgentName(dto.getAgentName());
		setAgentCardType(dto.getAgentCardType());
		setAgentCardID(dto.getAgentCardID());
		setGroupCampaignID(dto.getGroupCampaignID());
		setInstallationType(dto.getInstallationType());
		setReferjobcardID(dto.getReferJobCardID());
		setReferBookingSheetID(dto.getReferBookingSheetID());
		setContactPerson(dto.getContactPerson());
		setContactPhone(dto.getContactPhone());
		setPreferedDate(dto.getPreferedDate());
		setPreferedTime(dto.getPreferedTime());
		setPaymentStatus(dto.getPaymentStatus());
		setScheduleAction(dto.getScheduleAction());
		setScheduleTime(dto.getScheduleTime());
		setCallbackFlag(dto.getCallBackFlag());
		setStatusReason(dto.getStatusReason());
		setCreateOperatorId(dto.getCreateOperatorId());
		setStatus(dto.getStatus());
		setComments(dto.getComments());
		setBillCollectionMethod(dto.getBillCollectionMethod());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setCreateORGId(dto.getCreateORGId());
		setCallBackDate(dto.getCallBackDate());
		setServiceCodeList(dto.getServiceCodeList());
		setCreateReason(dto.getCreateReason()); 
		setPortNumber(dto.getPortNumber());
		setWalletId(dto.getWalletId());
		setPoint(dto.getPoint());
		setValue(dto.getValue());
		setAgentTelephone(dto.getAgentTelephone());
		setWorkDate(dto.getWorkDate());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustServiceInteractionDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	
	public abstract void setServiceCodeList(java.lang.String serviceCodeList);
	
	public abstract java.lang.String getServiceCodeList();
	
	public abstract void setAgentTelephone(java.lang.String agentTelephone);
	
    public abstract java.lang.String getAgentTelephone();
    
     public abstract void setCreateReason(java.lang.String createReason);
	
	public abstract java.lang.String getCreateReason();
	
	 public abstract void setPortNumber(int portNumber);
		
     public abstract int getPortNumber();
     
     public abstract void setValue(BigDecimal value);
		
     public abstract BigDecimal getValue();
     
     public abstract void setPoint(int point);
		
     public abstract int getPoint();
     
     public abstract void setWalletId(int walletId);

     public abstract int getWalletId();

	public abstract void setBillCollectionMethod(java.lang.String billCollectionMethod);
	
	public abstract java.lang.String getBillCollectionMethod();
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setReferSheetID(java.lang.String referSheetId);

	public abstract void setType(java.lang.String type);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setCreateOperatorId(int createOperatorId);
	
	public abstract int getCreateOperatorId();
	
	public abstract void setCustomerID(int customerId);

	public abstract void setAccountID(int accountId);

	public abstract void setAgentName(java.lang.String agentName);

	public abstract void setAgentCardType(java.lang.String agentCardType);

	public abstract void setAgentCardID(java.lang.String agentCardId);

	public abstract void setGroupCampaignID(int groupCampaignId);

	public abstract void setInstallationType(java.lang.String installationType);

	public abstract void setReferjobcardID(int referjobcardId);

	public abstract void setReferBookingSheetID(int referBookingSheetId);

	public abstract void setContactPerson(java.lang.String contactPerson);

	public abstract void setContactPhone(java.lang.String contactPhone);

	public abstract void setPreferedDate(java.sql.Timestamp preferedDate);
	
	public abstract void setCallBackDate(java.sql.Timestamp callBackDate);
	
	public abstract java.sql.Timestamp getCallBackDate();


	public abstract void setPreferedTime(java.lang.String preferedTime);

	public abstract void setPaymentStatus(java.lang.String paymentStatus);

	public abstract void setScheduleAction(java.lang.String scheduleAction);

	public abstract void setScheduleTime(java.sql.Timestamp scheduleTime);

	public abstract void setCallbackFlag(java.lang.String callbackFlag);

	public abstract void setStatusReason(java.lang.String statusReason);

	public abstract void setStatus(java.lang.String status);

	public abstract void setComments(java.lang.String comments);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCreateORGId(int createORGId);
	
	public abstract void setWorkDate(java.sql.Timestamp workDate);
	
	public abstract java.lang.Integer getId();

	public abstract java.lang.String getReferSheetID();

	public abstract java.lang.String getType();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getCustomerID();

	public abstract int getAccountID();

	public abstract java.lang.String getAgentName();

	public abstract java.lang.String getAgentCardType();

	public abstract java.lang.String getAgentCardID();

	public abstract int getGroupCampaignID();

	public abstract java.lang.String getInstallationType();

	public abstract int getReferjobcardID();

	public abstract int getReferBookingSheetID();

	public abstract java.lang.String getContactPerson();

	public abstract java.lang.String getContactPhone();

	public abstract java.sql.Timestamp getPreferedDate();

	public abstract java.lang.String getPreferedTime();

	public abstract java.lang.String getPaymentStatus();

	public abstract java.lang.String getScheduleAction();

	public abstract java.sql.Timestamp getScheduleTime();

	public abstract java.lang.String getCallbackFlag();

	public abstract java.lang.String getStatusReason();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getComments();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getCreateORGId();
	
	public abstract java.sql.Timestamp getWorkDate();
	
	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(CustServiceInteractionDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}

	}
}