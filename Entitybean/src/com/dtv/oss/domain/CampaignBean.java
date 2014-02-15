package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CampaignBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer campaignID)
			throws CreateException {
		//setCampaignID(campaignID);
		return null;
	}

	public java.lang.Integer ejbCreate(CampaignDTO dto) throws CreateException {
		/** @todo Complete this method */
		setDescription(dto.getDescription());
		setCampaignType(dto.getCampaignType());
		setDateFrom(dto.getDateFrom());
		setKeepBilling(dto.getKeepBilling());
		setDateTo(dto.getDateTo());
		setCustTypeList(dto.getCustTypeList());
		setOpenSourceTypeList(dto.getOpenSourceTypeList());
		setTimeLengthUnitNumber(dto.getTimeLengthUnitNumber());
		setTimeLengthUnitType(dto.getTimeLengthUnitType());
		setAllowPause(dto.getAllowPause());
		setAllowTransition(dto.getAllowTransition());
		setAllowTransfer(dto.getAllowTransfer());
		setAllowClose(dto.getAllowClose());
		setStatus(dto.getStatus());
		setObligationFlag(dto.getObligationFlag());
		setAllowAlter(dto.getAllowAlter());
		setCampaignName(dto.getCampaignName());
		setCsiTypeList(dto.getCsiTypeList());
		setRfBillingFlag(dto.getRfBillingFlag());
		setGroupBargainFlag(dto.getGroupBargainFlag());
		setAutoExtendFlag(dto.getAutoExtendFlag());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setRfBillingCycleFlag(dto.getRfBillingCycleFlag()); 
		setBundlePrePaymentFlag(dto.getBundlePrePaymentFlag());
		setPaymentAwardFlag(dto.getPaymentAwardFlag());
		setMultiCheckFlag(dto.getMultiCheckFlag());
		setCampainpriority(dto.getCampainpriority());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer campaignID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CampaignDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
    public abstract void setMultiCheckFlag(String multiCheckFlag);
	
	public abstract String getMultiCheckFlag();
	public abstract void setBundlePrePaymentFlag(java.lang.String bundlePrePaymentFlag);
	
	public abstract java.lang.String getBundlePrePaymentFlag();
	
	public abstract void setPaymentAwardFlag(java.lang.String rfBillingFlag);
	
	public abstract java.lang.String getPaymentAwardFlag();
	
	public abstract void setRfBillingFlag(java.lang.String rfBillingFlag);
	
	public abstract java.lang.String getRfBillingFlag();
	
	public abstract void setCsiTypeList(java.lang.String csiTypeList);
	
	public abstract java.lang.String getCsiTypeList();
	
	public abstract void setGroupBargainFlag(java.lang.String groupBargainFlag);
	
	public abstract java.lang.String getGroupBargainFlag();
	
	public abstract void setAutoExtendFlag(java.lang.String autoExtendFlag);
	
	public abstract java.lang.String getAutoExtendFlag();
	
	public abstract void setObligationFlag(java.lang.String obligationFlag);
	
	public abstract java.lang.String getObligationFlag();
	
	public abstract void setCampaignID(java.lang.Integer campaignID);

	public abstract void setTimeLengthUnitNumber(int timeLengthUnitNumber);

	public abstract void setTimeLengthUnitType(
			java.lang.String timeLengthUnitType);

	public abstract void setCampaignName(java.lang.String campaignName);

	public abstract void setAllowAlter(java.lang.String allowAlter);

	public abstract void setKeepBilling(java.lang.String keepBilling);

	public abstract void setDescription(java.lang.String description);

	public abstract void setDateFrom(java.sql.Timestamp dateFrom);

	public abstract void setDateTo(java.sql.Timestamp dateTo);

	public abstract void setOpenSourceTypeList(
			java.lang.String openSourceTypeList);

	public abstract void setAllowPause(java.lang.String allowPause);

	public abstract void setAllowTransition(java.lang.String allowTransition);

	public abstract void setAllowTransfer(java.lang.String allowTransfer);

	public abstract void setAllowClose(java.lang.String allowClose);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCampaignType(java.lang.String campaignType);

	public abstract void setCustTypeList(java.lang.String custTypeList);
	
	public abstract void setCampainpriority(int campainpriority);

	public abstract java.lang.Integer getCampaignID();

	public abstract java.lang.String getCampaignName();

	public abstract java.lang.String getDescription();

	public abstract java.lang.String getKeepBilling();

	public abstract java.sql.Timestamp getDateFrom();
	
	public abstract void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public abstract String getRfBillingCycleFlag();
	

	public abstract java.sql.Timestamp getDateTo();

	public abstract java.lang.String getOpenSourceTypeList();

	public abstract java.lang.String getAllowPause();

	public abstract java.lang.String getAllowTransition();

	public abstract java.lang.String getAllowTransfer();

	public abstract java.lang.String getAllowClose();

	public abstract java.lang.String getAllowAlter();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getCampaignType();

	public abstract java.lang.String getCustTypeList();
	
	public abstract int getCampainpriority();

	public abstract int getTimeLengthUnitNumber();

	public abstract String getTimeLengthUnitType();

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

	public int ejbUpdate(CampaignDTO dto) {
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